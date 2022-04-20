package cn.kissy.ecommerce.service.impl;

import cn.kissy.ecommerce.account.AddressInfo;
import cn.kissy.ecommerce.common.TableId;
import cn.kissy.ecommerce.dao.EcommerceAddressDao;
import cn.kissy.ecommerce.entity.EcommerceAddress;
import cn.kissy.ecommerce.filter.AccessContext;
import cn.kissy.ecommerce.service.IAddressService;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户地址相关服务接口实现
 * 这个实现类的特别在于：不能直接从请求获取用户信息，因为id可以被掉包。所以我们通过 jwtToken 传过来的信息接收用户信息作为强校验匹配
 * @ClassName AddressServiceImpl
 * @Author kingdee
 * @Date 2022/4/20
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements IAddressService {

    private final EcommerceAddressDao addressDao;

    public AddressServiceImpl(EcommerceAddressDao addressDao) {
        this.addressDao = addressDao;
    }

    /**
     * 存储多个地址信息
     * @param addressInfo
     * @return
     */
    @Override
    public TableId createAddressInfo(AddressInfo addressInfo) {
        // 先拿到对应的登录信息，不能从参数中直接获取用户的 id 信息
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 将传递的参数转换成实体对象
        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems().stream()
                .map(a -> EcommerceAddress.to(loginUserInfo.getId(), a))
                .collect(Collectors.toList());

        // 保存到数据表并把返回记录的 id 给客户端
        List<EcommerceAddress> saveRecords = addressDao.saveAll(ecommerceAddresses);
        List<Long> ids = saveRecords.stream().map(EcommerceAddress::getId).collect(Collectors.toList());
        log.info("create address info: [{}], [{}]", loginUserInfo.getId(), JSON.toJSONString(ids));

        // 因为TableId的类型是 List<Id> 所以ids需转化
        return new TableId(ids.stream().map(TableId.Id::new).collect(Collectors.toList()));
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        if (loginUserInfo == null){
            throw new RuntimeException("currentUserInfo is null, is not exist");
        }
        // 根据 userId 查询到用户的地址信息，再实现转换
        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllByUserId(loginUserInfo.getId());
        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(), addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {
        // orElse: 如果返回值存在则不生效，就直接会返回对象，不存在就直接返回null
        EcommerceAddress ecommerceAddress = addressDao.findById(id).orElse(null);
        if(null == ecommerceAddress){
            throw new RuntimeException("address is not exist");
        }

        // Collections返回单个的对象List
        return new AddressInfo(
                ecommerceAddress.getId(),
                Collections.singletonList(ecommerceAddress.toAddressItem())
        );
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {

        List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get address info by table id: [{}]", JSON.toJSONString(ids));

        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllById(ids);
        if (CollectionUtils.isEmpty(ecommerceAddresses)){
            return new AddressInfo(-1L, Collections.emptyList());
        }

        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(ecommerceAddresses.get(0).getUserId(), addressItems);
    }
}
