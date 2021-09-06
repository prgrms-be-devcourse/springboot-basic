package org.prgrms.kdt.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.customer.presentation.CustomerController;
import org.prgrms.kdt.voucher.presentation.VoucherController;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KdtWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

    @EnableWebMvc
    @Configuration
    @ComponentScan(
            basePackages =
                    {
                            "org.prgrms.kdt.voucher",
                            "org.prgrms.kdt.customer"
                    },
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    value = {
                            CustomerController.class,
                            VoucherController.class
                    }
            ),
            useDefaultFilters = false
    )
    static class ServletConfig implements WebMvcConfigurer, ApplicationContextAware {

        ApplicationContext applicationContext;

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
            springResourceTemplateResolver.setCharacterEncoding("UTF-8");
            springResourceTemplateResolver.setApplicationContext(applicationContext);
            springResourceTemplateResolver.setPrefix("/WEB-INF/views/");
            springResourceTemplateResolver.setSuffix(".html");


            SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
            springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);


            ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
            thymeleafViewResolver.setCharacterEncoding("UTF-8");
            thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
            thymeleafViewResolver.setViewNames(new String[]{"*"});
            registry.viewResolver(thymeleafViewResolver);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/resources/")
                    .setCachePeriod(60)
                    .resourceChain(true)
                    .addResolver(new EncodedResourceResolver()); //gzip으로 인코딩 --> 메모리 작게해줌
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
            XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
            messageConverter.setMarshaller(xStreamMarshaller);
            messageConverter.setUnmarshaller(xStreamMarshaller);

            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
            Jackson2ObjectMapperBuilder modules = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule);

            converters.add(0, new MappingJackson2HttpMessageConverter(modules.build()));
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("*");
        }
    }

    @Configuration
    @ComponentScan(
            basePackages =
                    {
                            "org.prgrms.kdt.voucher",
                            "org.prgrms.kdt.customer"
                    },
            excludeFilters = @ComponentScan.Filter
                    (
                            type = FilterType.ASSIGNABLE_TYPE,
                            value = {CustomerController.class, VoucherController.class}
                    )
    )
    @EnableTransactionManagement
    static class RootConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
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
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
        charEncodingFilterReg.setInitParameter("forceEncoding", "true");
        charEncodingFilterReg.addMappingForUrlPatterns(null, true, "/*");


        AnnotationConfigWebApplicationContext rootApplicationContext = new AnnotationConfigWebApplicationContext();
        rootApplicationContext.register(RootConfig.class);
        ContextLoaderListener loaderListener = new ContextLoaderListener(rootApplicationContext);
        servletContext.addListener(loaderListener);

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ServletConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        logger.info("init WebApplicationInitializer");
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("test", dispatcherServlet);
        servletRegistration.addMapping("/");
        servletRegistration.setLoadOnStartup(-1);


    }
}
