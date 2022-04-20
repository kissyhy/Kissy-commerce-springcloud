package cn.kissy.ecommerce.dao;

import cn.kissy.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户账户余额Dao接口定义
 * @ClassName EcommerceBalanceDao
 * @Author kingdee
 * @Date 2022/4/20
 **/
public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance, Long> {

    /**
     * 根据userId查询 EcommerceBalance 对象
     * @param userId
     * @return
     */
    EcommerceBalance findByUserId(Long userId);
}
