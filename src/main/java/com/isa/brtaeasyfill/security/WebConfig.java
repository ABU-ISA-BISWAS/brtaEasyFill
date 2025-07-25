package com.isa.brtaeasyfill.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@Configuration
public class WebConfig {

    private final IpRestrictionFilter ipRestrictionFilter;

    @Autowired
    public WebConfig(IpRestrictionFilter ipRestrictionFilter) {
        this.ipRestrictionFilter = ipRestrictionFilter;
    }

    @Bean
    public FilterRegistrationBean<IpRestrictionFilter> ipRestrictionFilterRegistration() {
        FilterRegistrationBean<IpRestrictionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(ipRestrictionFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }
}