//package com.programmers.springbootbasic.configuration;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.programmers.springbootbasic.controller"})
//public class WebAppConfig implements WebMvcConfigurer, ApplicationContextAware {
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp().viewNames("jsp/*");
//
//        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
//        springResourceTemplateResolver.setApplicationContext(applicationContext);
//        springResourceTemplateResolver.setPrefix("/WEB-INF/");
//        springResourceTemplateResolver.setSuffix(".html");
//
//        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//        springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);
//
//        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
//        thymeleafViewResolver.setOrder(1);
//        thymeleafViewResolver.setViewNames(new String[]{"views/*"});
//
//        registry.viewResolver(thymeleafViewResolver);
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/");
//    }
//
//}
