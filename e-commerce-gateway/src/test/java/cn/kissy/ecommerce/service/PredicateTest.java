package cn.kissy.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Java 8 Predicate 使用方法与思想
 * @ClassName PredicateTest
 * @Author kingdee
 * @Date 2022/4/11
 * @Version 1.0
 **/
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList(
            "nacos", "authority", "gateway", "ribbon", "feign", "hystrix", "e-commerce"
    );

    /**
     * test方法主要用于参数不符合规则，返回值是 boolean
     */
    @Test
    public void testPredicateTest(){
        // 使用Java8 语法(函数式编程)
        Predicate<String> letterLengthLimit = s -> s.length() > 5;

        // 找到 MICRO_SERVICE 里面字符串大于5的，打印出来
        MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }

    /**
     * and方法等同于我们的逻辑与 &&，存在短路特性，需要所有的条件都满足
     * 如果有一项不满足就返回 false
     */
    @Test
    public void testPredicateAnd(){
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterLengthLimit.and(letterStartWith)
        ).forEach(System.out::println);
    }

    /**
     * or方法等同于我们的逻辑或 ||，多个条件只要一个满足即可
     */
    @Test
    public void testPredicateOr(){
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterLengthLimit.or(letterStartWith)
        ).forEach(System.out::println);
    }

    /**
     * negate方法等同于逻辑非 ！，就是相反的意思
     */
    @Test
    public void testPredicateNegate(){
        Predicate<String> letterStartWith = s -> s.startsWith("gate");
        MICRO_SERVICE.stream().filter(letterStartWith.negate()).forEach(System.out::println);
    }

    /**
     * isEqual 类似于Object方法里的equals()
     * 区别在于：先判断对象是否为NULL，不为NULL的情况下再使用equals进行比较
     */
    @Test
    public void testPredicateIsEqual(){
        Predicate<String> equalGateway = s -> Predicate.isEqual("gateway").test(s);
        MICRO_SERVICE.stream().filter(equalGateway).forEach(System.out::println);
    }
}
