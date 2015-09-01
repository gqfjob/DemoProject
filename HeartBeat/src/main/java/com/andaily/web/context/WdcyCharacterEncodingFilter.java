package com.andaily.web.context;

import com.andaily.infrastructure.ThreadLocalHolder;
import com.andaily.web.WebUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Wrap the spring <i>CharacterEncodingFilter</i>, add retrieve client ip action
 *
 * @author Shengzhao Li
 */
public class WdcyCharacterEncodingFilter extends CharacterEncodingFilter {


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        persistIp(request);
        super.doFilterInternal(request, response, filterChain);

    }

    private void persistIp(HttpServletRequest request) {
        final String clientIp = WebUtils.retrieveClientIp(request);
        ThreadLocalHolder.clientIp(clientIp);
    }


}