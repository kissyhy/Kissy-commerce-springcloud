package cn.kissy.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import cn.kissy.ecommerce.dao.EcommerceUserDao;
import cn.kissy.ecommerce.entity.EcommerceUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName EcommerceUserTest
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Autowired
    public EcommerceUserDao userDao;

    @Test
    public void createUserDao(){
        EcommerceUser user = new EcommerceUser();
        user.setUsername("kissy");
        user.setPassword(MD5.create().digestHex("12345678"));
        user.setExtraInfo("{}");
        log.info("save user: [{}]", JSON.toJSON(userDao.save(user)));
    }
}
