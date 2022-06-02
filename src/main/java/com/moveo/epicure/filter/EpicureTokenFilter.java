package com.moveo.epicure.filter;


import com.moveo.epicure.dto.CustomerDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
            String token = httpRequest.getHeader("Authorization");
            try {
                token = token.substring(7);
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey("dfjbv87yfni4rht8hvfhb8r7eyehrdljfcnvbefjhisfhuisfuehghbgruonjv".getBytes())
                        .build().parseClaimsJws(token).getBody();

                CustomerDetail customerDetail = context.getBean(CustomerDetail.class);
                customerDetail.setId(Integer.parseInt(claims.getSubject()));

            } catch (Exception e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}