package cn.kissy.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户账户余额表 实体类定义
 * @ClassName EcommerceBalance
 * @Author kingdee
 * @Date 2022/4/20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_balance")
public class EcommerceBalance {

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 账户余额
     */
    @Column(name = "balance", nullable = false)
    private Long balance;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
