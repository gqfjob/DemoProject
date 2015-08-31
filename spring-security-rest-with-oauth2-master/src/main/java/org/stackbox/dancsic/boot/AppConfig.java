package org.stackbox.dancsic.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 * @author SRK.Lyu
 * @date 15/7/4
 */
@Configuration
@ComponentScan(basePackages = "org.stackbox", excludeFilters = @ComponentScan.Filter(Controller.class))
public class AppConfig {
}

