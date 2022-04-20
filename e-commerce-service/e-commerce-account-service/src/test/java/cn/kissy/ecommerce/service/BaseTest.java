package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.filter.AccessContext;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试用例基类，填充登录用户信息
 * @ClassName BaseTest
 * @Author kingdee
 * @Date 2022/4/20
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class BaseTest {

    protected final LoginUserInfo loginUserInfo = new LoginUserInfo(11L, "kissy");

    @Before
    public void init(){
        AccessContext.setLoginUserInfo(loginUserInfo);
    }

    @After
    public void destroy(){
        AccessContext.clearLoginUserInfo();
    }
}
