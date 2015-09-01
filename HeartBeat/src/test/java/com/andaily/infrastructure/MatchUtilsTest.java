package com.andaily.infrastructure;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class MatchUtilsTest {


    @Test
    public void isEmail() {

        boolean result = MatchUtils.isEmail("addd");
        assertFalse(result);

        result = MatchUtils.isEmail("addd@honyee.cc");
        assertTrue(result);
    }

}