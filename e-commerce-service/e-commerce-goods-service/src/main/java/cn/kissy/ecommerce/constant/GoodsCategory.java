package cn.kissy.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 商品类别
 * @ClassName GoodsCategory
 * @Author kingdee
 * @Date 2022/4/22
 **/
@Getter
@AllArgsConstructor
public enum GoodsCategory {

    DIAN_QI("10001", "电器"),
    JIA_JU("10002", "家具"),
    FU_SHI("10003", "服饰"),
    MU_YIN("10004", "母婴"),
    SHI_PIN("10005", "食品"),
    TU_SHU("10006", "图书"),
    ;


    /**
     * 商品分类编码
     */
    private final String code;

    /**
     * 商品分类描述信息
     */
    private final String description;

    /**
     * 根据 code 获取到 GoodsCategory
     * @param code
     * @return
     */
    public static GoodsCategory of(String code){
        Objects.requireNonNull(code);

        return Stream.of(values())
                // 过滤掉不相同的code
                .filter(bean -> bean.code.equals(code))
                // 寻找到任何一个
                .findAny()
                // 找不到就抛出异常
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists")
                );
    }
}
