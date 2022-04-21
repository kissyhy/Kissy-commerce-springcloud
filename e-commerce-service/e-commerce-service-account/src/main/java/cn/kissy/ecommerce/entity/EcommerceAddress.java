package cn.kissy.ecommerce.entity;

import cn.kissy.ecommerce.account.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户地址表实体类定义
 * @ClassName EcommerceAddress
 * @Author kingdee
 * @Date 2022/4/19
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_address")
public class EcommerceAddress {

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
     * 用户名
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 电话
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 省
     */
    @Column(name = "province", nullable = false)
    private String province;

    /**
     * 市
     */
    @Column(name = "city", nullable = false)
    private String city;

    /**
     * 详细地址
     */
    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 创建时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 根据 userId + AddressItem 得到 EcommerceAddress
     * @param userId
     * @param addressItem
     * @return
     */
    public static EcommerceAddress to(Long userId, AddressInfo.AddressItem addressItem){
        EcommerceAddress ecommerceAddress = new EcommerceAddress();

        ecommerceAddress.setUserId(userId);
        ecommerceAddress.setUsername(addressItem.getUsername());
        ecommerceAddress.setPhone(addressItem.getPhone());
        ecommerceAddress.setProvince(addressItem.getProvince());
        ecommerceAddress.setCity(addressItem.getCity());
        ecommerceAddress.setAddressDetail(addressItem.getAddressDetail());

        return ecommerceAddress;
    }

    /**
     * 将 EcommerceAddress 对象转换成 AddressInfo
     * @return
     */
    public AddressInfo.AddressItem toAddressItem(){
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();

        addressItem.setId(this.id);
        addressItem.setUsername(this.username);
        addressItem.setPhone(this.phone);
        addressItem.setProvince(this.province);
        addressItem.setCity(this.city);
        addressItem.setAddressDetail(this.addressDetail);
        addressItem.setCreateTime(this.createTime);
        addressItem.setUpdateTime(this.updateTime);

        return addressItem;
    }
}
