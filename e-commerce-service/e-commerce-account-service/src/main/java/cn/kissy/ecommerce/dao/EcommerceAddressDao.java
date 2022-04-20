package cn.kissy.ecommerce.dao;

import cn.kissy.ecommerce.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * EcommerceAddress Dao 接口定义
 * @ClassName EcommerceAddressDao
 * @Author kingdee
 * @Date 2022/4/20
 **/
public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress, Long> {

    /**
     * 根据用户id查询地址信息
     * @param userId
     * @return
     */
    List<EcommerceAddress> findAllByUserId(Long userId);
}
