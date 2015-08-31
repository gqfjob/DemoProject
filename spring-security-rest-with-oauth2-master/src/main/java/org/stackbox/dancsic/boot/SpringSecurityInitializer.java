package org.stackbox.dancsic.boot;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author SRK.Lyu
 * @date 15/7/5
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer{

}
