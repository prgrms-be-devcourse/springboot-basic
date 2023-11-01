package com.programmers.springbootbasic;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.HiddenHttpMethodFilter;
@Profile(value = "tomcat")
@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new HiddenHttpMethodFilter());
        return filterRegBean;
    }
}
