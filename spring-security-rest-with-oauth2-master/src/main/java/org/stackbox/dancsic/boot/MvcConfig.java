package org.stackbox.dancsic.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletContext;

/**
 * @author SRK.Lyu
 * @date 15/7/4
 */
@Configuration
@ComponentScan(basePackages = "org.stackbox")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrefixJson(false);

        registry.jsp("/WEB-INF/jsp/", ".jsp");
        registry.enableContentNegotiation();
    }



}
