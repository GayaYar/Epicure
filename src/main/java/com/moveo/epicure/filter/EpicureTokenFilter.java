package com.moveo.epicure.filter;


import com.moveo.epicure.dto.UserDetail;
import com.moveo.epicure.util.TokenUtil;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EpicureTokenFilter implements Filter, ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String method = httpRequest.getMethod();
        if (method.equals("OPTIONS")) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            try{
                Claims claims = TokenUtil.validateAndGetClaims(httpRequest);
                UserDetail userDetail = context.getBean(UserDetail.class);
                userDetail.setId(Integer.parseInt(claims.getSubject()));
                userDetail.setName(claims.get("customerName").toString());

            } catch (Exception e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
