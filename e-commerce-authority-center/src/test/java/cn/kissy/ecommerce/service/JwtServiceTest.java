package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.util.TokenParseUtil;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Jwt 相关服务测试类
 * @ClassName JwtServiceTest
 * @Author kingdee
 * @Date 2022/4/8
 * @Version 1.0
 **/
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtServiceTest {

    @Autowired
    private IJWTService ijwtService;

    @Test
    public void testGenerateAndParseToken() throws Exception{
        // 授权token
        String jwtToken = ijwtService.generateToken(
                "kissy",
                "25d55ad283aa400af464c76d713c07ad"
        );
        log.info("jwt token is [{}]", jwtToken);

        // 颁发给客户端
        LoginUserInfo userInfo = TokenParseUtil.parseUserInfoFromToken(jwtToken);
        log.info("parse token: [{}]", JSON.toJSONString(userInfo));
    }
}
