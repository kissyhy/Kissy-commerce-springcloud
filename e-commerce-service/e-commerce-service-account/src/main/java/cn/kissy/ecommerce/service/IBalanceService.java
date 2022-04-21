package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.account.BalanceInfo;

/**
 * 用于余额相关的服务接口定义
 * @ClassName IBalanceService
 * @Author kingdee
 * @Date 2022/4/20
 **/
public interface IBalanceService {

    /**
     * 获取当前用户余额信息
     * @return
     */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     * 扣减用户余额
     * @param balanceInfo 代表我想要扣减的余额
     * @return
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
