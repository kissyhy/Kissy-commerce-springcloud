package cn.kissy.ecommerce.controller;

import cn.kissy.ecommerce.account.BalanceInfo;
import cn.kissy.ecommerce.service.IBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户余额服务对外接口
 * @ClassName BalanceController
 * @Author kingdee
 * @Date 2022/4/21
 **/
@Api(tags = "用户余额服务")
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private IBalanceService balanceService;

    @GetMapping("/current-balance")
    @ApiOperation(value = "当前用户", notes = "获取用户余额信息", httpMethod = "GET")
    public BalanceInfo getCurrentUserBalanceInfo(){
        return balanceService.getCurrentUserBalanceInfo();
    }

    @PutMapping("/deduct-balance")
    @ApiOperation(value = "扣减", notes = "扣减用户余额", httpMethod = "PUT")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo){
        return balanceService.deductBalance(balanceInfo);
    }

}
