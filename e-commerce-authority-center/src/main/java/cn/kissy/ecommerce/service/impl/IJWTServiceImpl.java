package cn.kissy.ecommerce.service.impl;

import cn.kissy.ecommerce.constant.AuthorityConstant;
import cn.kissy.ecommerce.constant.CommonConstant;
import cn.kissy.ecommerce.dao.EcommerceUserDao;
import cn.kissy.ecommerce.entity.EcommerceUser;
import cn.kissy.ecommerce.service.IJWTService;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import cn.kissy.ecommerce.vo.UsernameAndPassword;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 相关服务接口实现
 * @ClassName IJWTServiceImpl
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IJWTServiceImpl implements IJWTService {

    @Autowired
    private EcommerceUserDao userDao;

    @Override
    public String generateToken(String username, String password) throws Exception {
        // 直接返回一个0天，就会默认生成1天
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expire) throws Exception {

        // 1. 首先需要验证用户是否能通过授权校验，即输入的用户名和密码能否匹配数据表记录
        EcommerceUser ecommerceUser = userDao.findByUsernameAndPassword(username, password);
        if (null == ecommerceUser){
            log.error("Can not find user: [{}]", username, password);
            return null;
        }

        // 2. 让jwtToken中塞入对象，即JWT中存储的信息，后端拿到这些信息就可以知道是哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(
                ecommerceUser.getId(), ecommerceUser.getUsername()
        );
        // 如果传进来的时间超过小于1天，那么就重新赋值
        if (expire <= 0){
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        // 3. 计算超时时间(带有时区的zdt)，拿取当前的时间+天
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS).atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // Jwt payload部分，就是真实的数据 --> key,value对象
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSON(loginUserInfo))
                // jwt id 实际上没什么作用，就是标记为一个jwt仅此而已
                .setId(UUID.randomUUID().toString())
                // jwt 过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {

        // 校验用户名是否存在，如果存在，不能重复注册
        EcommerceUser oldUser = userDao.findByUsername(usernameAndPassword.getUsername());
        if (oldUser != null){
            log.error("Username is registered: [{}]", oldUser.getUsername());
            return null;
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        ecommerceUser.setPassword(usernameAndPassword.getPassword()); // MD5编码以后的密码
        ecommerceUser.setExtraInfo("{}");

        // 注册一个新用户，写一条记录到数据表中
        ecommerceUser = userDao.save(ecommerceUser);
        log.info("register user success: [{}]", ecommerceUser.getUsername(), ecommerceUser.getId());

        // 获取新用户之后，就可以生成Token并返回
        return generateToken(ecommerceUser.getUsername(), ecommerceUser.getPassword());
    }

    /**
     * 根据本地存储的私钥获取到 Privatekey 对象
     * @return
     * @throws Exception
     */
    private PrivateKey getPrivateKey() throws Exception{
        // 把 privateKey 通过 decode 传入 PKCS8 这个key中
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY)
        );

        // 因为我们是通过RSA非对称加密进行加密，用keyFactory拿到RSA的实例信息
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 最后把 priPKCS8 这个 key 传入 keyFactory当中
        return keyFactory.generatePrivate(priPKCS8);
    }
}
