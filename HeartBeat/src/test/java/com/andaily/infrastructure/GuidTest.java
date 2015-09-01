package com.andaily.infrastructure;

import com.andaily.domain.shared.GuidGenerator;
import org.testng.annotations.Test;

/**
 * @author Shengzhao Li
 */
public class GuidTest {


    @Test
    public void guid() {
        for (int i = 0; i < 5; i++) {
            String generate = GuidGenerator.generate();
            System.out.println(generate);
        }
    }

}