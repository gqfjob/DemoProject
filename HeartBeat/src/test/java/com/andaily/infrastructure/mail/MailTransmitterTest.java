package com.andaily.infrastructure.mail;

import com.andaily.ContextTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class MailTransmitterTest extends ContextTest {


    @Test(enabled = false)
    public void testSend() throws Exception {

        MailTransmitter transmitter = new MailTransmitter("HeartBeat Testing", "I'm HeartBeat, just for Testing send mail... <br/> Please ignore...", "shengzhao@andaily.com");
        final MailTransmitResult result = transmitter.transmit();
        assertNotNull(result);
        assertTrue(result.success());

    }

}