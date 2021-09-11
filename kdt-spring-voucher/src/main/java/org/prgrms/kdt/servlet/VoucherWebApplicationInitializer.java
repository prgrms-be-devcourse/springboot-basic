package org.prgrms.kdt.servlet;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.config.ServletConfiguration;
import org.prgrms.kdt.controller.CustomerController;
import org.prgrms.kdt.jdbcRepository.CustomerJdbcRepository;
import org.prgrms.kdt.service.SimpleCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

public class VoucherWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(VoucherWebApplicationInitializer.class);

//    @Configuration
//    @ComponentScan(basePackages = { "org.prgrms.kdt.controller","org.prgrms.kdt.domain"
//            ,"org.prgrms.kdt.service","org.prgrms.kdt.jdbcRepository" },
//            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {SimpleCustomerService.class,
//                    CustomerJdbcRepository.class})
//            , useDefaultFilters = false
//    )
//    @EnableTransactionManagement
//    static class RootConfig{
//
//        @Bean
//        public DataSource dataSource(){
//            var datasource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:3306/voucher_order_mgmt")
//                    .username("root")
//                    .password("root1234!")
//                    .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
//                    .build();
//
//            datasource.setMaximumPoolSize(100);
//            datasource.setMinimumIdle(100);
//            return datasource;
//        }
//
//        @Bean
//        public JdbcTemplate jdbcTemplate(DataSource dataSource){
//            return new JdbcTemplate(dataSource);
//        }
//
//        @Bean
//        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
//            return new NamedParameterJdbcTemplate(jdbcTemplate);
//        }
//
//        @Bean
//        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
//            return new DataSourceTransactionManager(dataSource);
//        }
//
//    }

    /*
    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = { "org.prgrms.kdt.controller","org.prgrms.kdt.domain"
            ,"org.prgrms.kdt.service","org.prgrms.kdt.jdbcRepository" },
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {SimpleCustomerService.class,
                    CustomerJdbcRepository.class, CustomerController.class})
            , useDefaultFilters = false
    )
    @EnableTransactionManagement
    static class ServletConfig implements WebMvcConfigurer, ApplicationContextAware {

        ApplicationContext applicationContext;

//        @Bean
//        public DataSource dataSource(){
//            var datasource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost:3306/voucher_order_mgmt")
//                    .username("root")
//                    .password("root1234!")
//                    .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
//                    .build();
//
//            datasource.setMaximumPoolSize(100);
//            datasource.setMinimumIdle(100);
//            return datasource;
//        }


        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {

            //타임 리프 설정
            SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
            springResourceTemplateResolver.setApplicationContext(applicationContext);
            springResourceTemplateResolver.setPrefix("/WEB-INF/");
            springResourceTemplateResolver.setSuffix(".html");

            SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
            springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);

            //타입 리프 view resolver
            ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
            thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
            thymeleafViewResolver.setOrder(1);
            thymeleafViewResolver.setViewNames(new String[]{"views/*"});

            registry.viewResolver(thymeleafViewResolver);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
        }

    }
*/


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("servlet 실행!!! ");
        System.out.println("실행됐다고 말해!!!!!! ");

        var rootApplicationContext = new AnnotationConfigWebApplicationContext();
        rootApplicationContext.register(AppConfiguration.class);
        ContextLoaderListener loaderListener = new ContextLoaderListener(rootApplicationContext);
        servletContext.addListener(loaderListener);

        var applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ServletConfiguration.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("test", dispatcherServlet);
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }
}
