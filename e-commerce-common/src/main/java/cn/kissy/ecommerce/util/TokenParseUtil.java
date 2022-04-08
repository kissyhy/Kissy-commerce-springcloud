package cn.kissy.ecommerce.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.kissy.ecommerce.constant.CommonConstant;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;

/**
 * JwtToken 解析工具类
 * @ClassName TokenParseUtil
 * @Author kingdee
 * @Date 2022/4/8
 * @Version 1.0
 **/
@Slf4j
public class TokenParseUtil {

    /**
     * 从JWT Token中解析 LoginUserInfo对象
     * @param token
     * @return
     * @throws Exception
     */
    public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception{
        if (token == null){
            return null;
        }
        // Jwt payload部分，就是真实的数据 --> key,value对象
        // payload的部分就是通过jwt.builder().claim(key, value)形式转换的
        // 所以 Jws<Claims> 里的Claims就是存储 LoginUserInfo的信息
        Jws<Claims> claimsJws = parseToken(token, getPublicKey());
        Claims body = claimsJws.getBody();

        // 通过拿取body中的过期时间，与现在的时间进行比对
        // 如果token已经过期了，返回null
        if (body.getExpiration().before(Calendar.getInstance().getTime())){
            return null;
        }

        // 返回token中保存的用户信息
//        return JSON.parseObject(
//                body.get(CommonConstant.JWT_USER_INFO_KEY).toString(),
//                LoginUserInfo.class
//        );
        JSONObject jsonObject = JSONUtil.parseObj(body.get(CommonConstant.JWT_USER_INFO_KEY));
        return JSONUtil.toBean(jsonObject, LoginUserInfo.class);
    }

    /**
     * 通过公钥解析JwtToken
     * @param token
     * @param publicKey
     * @return
     */
    private static Jws<Claims> parseToken(String token, PublicKey publicKey){
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }


    /**
     * 根据本地存储的公钥 获取到PublicKey对象
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey() throws Exception{
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PUBLIC_KEY)
        );
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}
