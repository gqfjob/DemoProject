package com.andaily.domain.shared;

import com.andaily.domain.user.SystemSetting;
import com.andaily.domain.user.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Shengzhao Li
 */
public class Application implements InitializingBean {

    //系统字符编码
    public static final String ENCODING = "UTF-8";

    public static final String PROJECT_HOME = "https://git.oschina.net/mkk/HeartBeat/";
    //Current development version
    public static final String CURRENT_VERSION = "0.4";

    //application host
    private static String host;

    private static SystemSetting systemSetting;

    /*
    * default
    * */
    public Application() {
    }


    public static String host() {
        return host;
    }

    public void setHost(String host) {
        Application.host = host;
    }


    public static SystemSetting systemSetting() {
        checkingAndInitialSystemSetting();
        return systemSetting;
    }

    private static void checkingAndInitialSystemSetting() {
        if (systemSetting == null) {
            UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
            systemSetting = userRepository.findSystemSetting();
            Assert.notNull(systemSetting, "systemSetting is null");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(host, "host is null");
    }
}