package com.andaily.infrastructure;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * @author Shengzhao Li
 */
public class PasswordHandlerTest {

    @Test
    public void testEncryptPassword() throws Exception {
        final String encryptPass = PasswordHandler.encryptPassword("admin");
        assertNotNull(encryptPass);
        System.out.println(encryptPass);

    }
}