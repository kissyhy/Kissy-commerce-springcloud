package cn.kissy.ecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类定义
 * @ClassName EcommerceUser
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_user")
public class EcommerceUser implements Serializable {

    /**
     * 自增主键
     */

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * MD5密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 额外的信息，json字符串存储
     */
    @Column(name = "extra_info", nullable = false)
    private String extraInfo;

    /**
     * 创建时间
     */
    @CreatedDate //自动生成创建日期
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
