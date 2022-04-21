package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.account.AddressInfo;
import cn.kissy.ecommerce.common.TableId;

/**
 * 用户地址相关服务接口定义
 * @ClassName IAddressService
 * @Author kingdee
 * @Date 2022/4/20
 **/
public interface IAddressService {

    /**
     * 创建用户地址信息
     * @param addressInfo
     * @return 主键id
     */
    TableId createAddressInfo(AddressInfo addressInfo);

    /**
     * 获取当前登录的用户地址信息
     * @return
     */
    AddressInfo getCurrentAddressInfo();

    /**
     * 通过 id 获取用户地址信息，id是Ecommerce表的主键
     * @param id
     * @return
     */
    AddressInfo getAddressInfoById(Long id);

    /**
     * 通过 TableId 获取用户地址信息
     * @param tableId
     * @return
     */
    AddressInfo getAddressInfoByTableId(TableId tableId);

}
