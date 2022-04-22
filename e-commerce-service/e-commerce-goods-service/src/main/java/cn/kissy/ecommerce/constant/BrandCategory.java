package cn.kissy.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 品牌分类枚举类
 * @ClassName BrandCategory
 * @Author kingdee
 * @Date 2022/4/22
 **/
@Getter
@AllArgsConstructor
public enum BrandCategory {

    BRAND_A("20001", "爱马仕"),
    BRAND_B("20002", "蔻驰"),
    BRAND_C("20003", "香奈儿"),
    BRAND_D("20004", "PARDA"),
    BRAND_E("20005", "GUCCI"),
    ;


    /**
     * 品牌分类编码
     */
    private final String code;

    /**
     * 品牌分类描述信息
     */
    private final String description;

    /**
     * 根据 code 获取到 BrandCategory
     * @param code
     * @return
     */
    public static BrandCategory of(String code){
        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists")
                );

    }
}
