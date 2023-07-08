package org.prgrms.application.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;

public class ApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

    @EnableWebMvc
    @Configuration
    @EnableTransactionManagement
    @ComponentScan(
            basePackages = {"org.prgrms.application.domain.voucher", "org.prgrms.application.repository.voucher","org.prgrms.application.service","org.prgrms.application.controller"}
    )
    static class RootConfig implements WebMvcConfigurer, ApplicationContextAware {
            ApplicationContext applicationContext;

            @Override
            public void configureViewResolvers(ViewResolverRegistry registry){

                SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
                springResourceTemplateResolver.setApplicationContext(applicationContext);
                springResourceTemplateResolver.setPrefix("/WEB-INF/");
                springResourceTemplateResolver.setSuffix(".html");
                SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
                springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);

                ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
                thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
                thymeleafViewResolver.setOrder(1);
                thymeleafViewResolver.setViewNames(new String[]{"views/*"});
                registry.viewResolver(thymeleafViewResolver);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry){
                registry.addResourceHandler("/resources/**")
                        .addResourceLocations("/resources/")
                        .setCachePeriod(60)
                        .resourceChain(true)
                        .addResolver(new EncodedResourceResolver());
            }

            @Bean
            public DataSource dataSource() {
                return new EmbeddedDatabaseBuilder()
                        .generateUniqueName(true)
                        .setType(EmbeddedDatabaseType.H2)
                        .setScriptEncoding("UTF-8")
                        .ignoreFailedDrops(true)
                        .build();
            }

            @Bean
            public JdbcTemplate jdbcTemplate(DataSource dataSource) {
                return new JdbcTemplate(dataSource);
            }

            @Bean
            public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
                return new NamedParameterJdbcTemplate(jdbcTemplate);
            }

            @Bean
            public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
                return new DataSourceTransactionManager(dataSource);
            }

            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                this.applicationContext = applicationContext;
            }
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        logger.info("Starting Server...");

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(RootConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("test", dispatcherServlet);
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(1);
    }
}
