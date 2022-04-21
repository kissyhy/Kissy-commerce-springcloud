package cn.kissy.ecommerce.controller;

import cn.kissy.ecommerce.account.AddressInfo;
import cn.kissy.ecommerce.common.TableId;
import cn.kissy.ecommerce.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户地址服务对外接口
 * @ClassName AddressController
 * @Author kingdee
 * @Date 2022/4/21
 **/
@Api(tags = "用户地址服务")
@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping("/create-address")
    // value是简述, notes是详细的描述信息
    @ApiOperation(value = "创建", notes = "创建用户地址信息", httpMethod = "POST")
    public TableId createAddressInfo(@RequestBody AddressInfo addressInfo){
        return addressService.createAddressInfo(addressInfo);
    }

    @GetMapping("/current-address")
    // value是简述, notes是详细的描述信息
    @ApiOperation(value = "当前用户", notes = "获取当前登录用户地址信息", httpMethod = "GET")
    public AddressInfo getCurrentAddressInfo(){
        return addressService.getCurrentAddressInfo();
    }

    @GetMapping("/address-info")
    @ApiOperation(value = "获取用户地址信息", notes = "通过id获取用户地址信息，id是EcommerceAddress表的主键", httpMethod = "GET")
    public AddressInfo getAddressInfoById(@RequestParam Long id){
        return addressService.getAddressInfoById(id);
    }

    @PostMapping("/address-info-by-table-id")
    @ApiOperation(value = "获取用户地址信息", notes = "通过table_id获取用户地址信息", httpMethod = "POST")
    public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId){
        return addressService.getAddressInfoByTableId(tableId);
    }
}
