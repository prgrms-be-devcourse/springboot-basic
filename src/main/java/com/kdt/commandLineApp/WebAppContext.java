package com.kdt.commandLineApp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages ="com.kdt.commandLineApp")
@PropertySource("application.yaml")
public class WebAppContext implements WebMvcConfigurer, ApplicationContextAware {
    ApplicationContext applicationContext;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp().viewNames("jsp/*");

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public DataSource dataSource(@Value("${db_driver_class}") String driver, @Value("${db_url}") String url, @Value("${db_user}") String user, @Value("${db_pwd}") String pwd) {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(user)
                .password(pwd)
                .type(MysqlDataSource.class)
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
