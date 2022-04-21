package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.account.AddressInfo;
import cn.kissy.ecommerce.common.TableId;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * 用户地址相关服务功能测试
 * @ClassName AddressServiceTest
 * @Author kingdee
 * @Date 2022/4/20
 **/
@Slf4j
public class AddressServiceTest extends BaseTest{

    @Autowired
    private IAddressService addressService;

    /**
     * 测试创建用户地址信息
     */
    @Test
    public void testCreateAddressInfo(){
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("kissy");
        addressItem.setPhone("188888888");
        addressItem.setProvince("广东省");
        addressItem.setCity("清远市");
        addressItem.setAddressDetail("光明派出所");
        log.info("test create address info: [{}]", JSON.toJSONString(addressService.createAddressInfo(
                new AddressInfo(loginUserInfo.getId(), Collections.singletonList(addressItem))
        )));
    }

    /**
     * 测试获取当前登录用户地址信息
     */
    @Test
    public void testGetCurrentAddressInfo(){
        log.info("test get current user info: [{}]", JSON.toJSONString(
                addressService.getCurrentAddressInfo()
        ));
    }

    /**
     * 测试通过id获取用户地址信息
     */
    @Test
    public void testGetAddressInfoById(){
        log.info("test get address info by id: [{}]", addressService.getAddressInfoById(10L));
    }

    /**
     * 测试通过TableId获取用户地址信息
     */
    @Test
    public void testGetAddressByTableId(){
        log.info("test get address info by tableId: [{}]", JSON.toJSONString(
                addressService.getAddressInfoByTableId(new TableId(
                        Collections.singletonList(new TableId.Id(10L))
                ))
        ));
    }
}
