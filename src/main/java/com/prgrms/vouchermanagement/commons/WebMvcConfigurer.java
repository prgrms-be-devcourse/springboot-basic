package com.prgrms.vouchermanagement.commons;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.prgrms.vouchermanagement.commons.page.PageArgumentResolver;

@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(this.pageArgumentResolver());
	}

	@Bean
	public PageArgumentResolver pageArgumentResolver() {
		return new PageArgumentResolver();
	}
}
