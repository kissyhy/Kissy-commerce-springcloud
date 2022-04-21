package cn.kissy.ecommerce.service.impl;

import cn.kissy.ecommerce.account.BalanceInfo;
import cn.kissy.ecommerce.dao.EcommerceBalanceDao;
import cn.kissy.ecommerce.entity.EcommerceBalance;
import cn.kissy.ecommerce.filter.AccessContext;
import cn.kissy.ecommerce.service.IBalanceService;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于余额相关服务接口实现
 * @ClassName BalanceServiceImpl
 * @Author kingdee
 * @Date 2022/4/21
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    @Autowired
    private EcommerceBalanceDao balanceDao;

    /**
     * 获取用户余额，没有就创建
     * @return
     */
    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        // 获取用户信息
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        // 通过用户信息生成一个新的 balanceInfo 对象，最终返回 balanceInfo
        BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(), 0L);

        // 向数据库查询并获取用户余额记录
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        // 如果不为空，更新余额记录
        if (null != ecommerceBalance){
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            // 如果没有用户余额记录，就创建一条余额记录，余额设定为0即可
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            log.info("init user balance record: [{}]", balanceDao.save(newBalance).getId());
        }

        return balanceInfo;
    }

    /**
     * 扣减用户余额
     * @param balanceInfo 代表我想要扣减的余额
     * @return
     */
    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 扣减用户余额的一个基本原则：扣减额 <= 当前用户余额
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        if (null == ecommerceBalance || ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0){
            throw new RuntimeException("user balance is not enough!");
        }

        // 原来的余额
        Long sourceBalance = ecommerceBalance.getBalance();

        // 更新并存入数据库
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        log.info("deduct balance: [{}], [{}], [{}]", balanceDao.save(ecommerceBalance).getId(), sourceBalance, balanceInfo.getBalance());

        return new BalanceInfo(ecommerceBalance.getUserId(), ecommerceBalance.getBalance());
    }
}
