package cn.kissy.ecommerce.filter;

import cn.kissy.ecommerce.vo.LoginUserInfo;

/**
 * 使用 ThreadLocal 去单独存储每一个线程携带的 LoginUserInfo
 * 要及时的清理我们保存到 ThreadLocal 中的用户信息：
 *  1. 保证没有资源泄露
 *  2. 保证线程在重用时，不会出现数据混乱
 * @ClassName AccessContext
 * @Author kingdee
 * @Date 2022/4/19
 **/
public class AccessContext {

    /**
     * 线程池
     */
    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    /**
     * get set 方法
     * @return
     */
    public static LoginUserInfo getLoginUserInfo(){
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo userInfo){
        loginUserInfo.set(userInfo);
    }

    public static void clearLoginUserInfo() {
        loginUserInfo.remove();
    }
}
