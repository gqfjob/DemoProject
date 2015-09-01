package com.andaily;

import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.security.AndailyUserDetails;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.domain.shared.security.AndailyUserDetails;
import com.andaily.web.context.SpringSecurityHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author Shengzhao Li
 */
@ContextConfiguration(locations = {"classpath:/spring/*.xml"}, initializers = {TestApplicationContextInitializer.class})
public abstract class ContextTest extends AbstractTransactionalTestNGSpringContextTests {


    @BeforeTransaction
    public void before() throws Exception {
        BeanProvider.initialize(applicationContext);
        SecurityUtils securityUtils = new SecurityUtils();
        securityUtils.setSecurityHolder(new SpringSecurityHolder() {
            @Override
            public AndailyUserDetails userDetails() {
                return null;
            }
        });
    }
}