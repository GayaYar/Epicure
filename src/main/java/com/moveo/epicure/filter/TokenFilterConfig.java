package com.moveo.epicure.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenFilterConfig {

    @Bean
    public FilterRegistrationBean<EpicureTokenFilter> registerFilter() {
        FilterRegistrationBean<EpicureTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(epicureTokenFilter());
        registrationBean.addUrlPatterns("/customers/cart/*", "/customers/order", "/restaurants/add");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public EpicureTokenFilter epicureTokenFilter() {
        return new EpicureTokenFilter();
    }
}
