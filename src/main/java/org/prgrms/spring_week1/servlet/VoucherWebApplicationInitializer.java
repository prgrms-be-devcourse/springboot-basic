package org.prgrms.spring_week1.servlet;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.sql.DataSource;
import org.prgrms.spring_week1.Voucher.controller.VoucherController;
import org.prgrms.spring_week1.customer.controller.CustomerController;
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
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


public class VoucherWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(VoucherWebApplicationInitializer.class);

    // Controller만
    @EnableWebMvc // spring mvc가 필요한 bean들 자동등록
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.spring_week1.Voucher", "org.prgrms.spring_week1.customer"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherController.class),
        useDefaultFilters = false)
    static class ServletConfig implements WebMvcConfigurer, ApplicationContextAware { // WebMvcConfigurer: MVC에 대해 특정한 설정을 해주기 위해
        ApplicationContext applicationContext;

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {

            // thymeleaf
            SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
            springResourceTemplateResolver.setApplicationContext(applicationContext);

            springResourceTemplateResolver.setPrefix("/WEB-INF/");
            springResourceTemplateResolver.setSuffix(".html");
            SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
            springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);

            ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
            thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
            thymeleafViewResolver.setOrder(1);
            thymeleafViewResolver.setViewNames(new String[]{"views/*"}); // view하위에 모든 template는 thymeleaf사용
            registry.viewResolver(thymeleafViewResolver);
        }


        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
            MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
            XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
            marshallingHttpMessageConverter.setMarshaller(xStreamMarshaller);
            marshallingHttpMessageConverter.setUnmarshaller(xStreamMarshaller);
            converters.add(0, marshallingHttpMessageConverter);


            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
                DateTimeFormatter.ISO_DATE_TIME));
            Jackson2ObjectMapperBuilder modules = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule);
            converters.add(1, new MappingJackson2HttpMessageConverter(modules.build()));
        }


        @Bean
        public TransactionTemplate transactionTemplate(
            PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**") // 이렇게 들어오는 요청
                .allowedMethods("POST")
                .allowedOrigins("*"); // 전체허용
        }
    }

    // Service, Repository만
    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = {"org.prgrms.spring_week1.Voucher", "org.prgrms.spring_week1.customer"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherController.class)
    )
    static class RootConfig {
        @Bean
        public DataSource dataSource() {

            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/week1")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class)
                .build();

            // thread 수 조절가능
            dataSource.setMaximumPoolSize(100);
            dataSource.setMinimumIdle(100);

            return dataSource;

        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        ;

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
    public void onStartup(ServletContext servletContext) throws ServletException {

        // root applicationContext
        logger.info("Starting Server...");
        AnnotationConfigWebApplicationContext rootApplicationContext = new AnnotationConfigWebApplicationContext();
        rootApplicationContext.register(RootConfig.class);
        ContextLoaderListener loaderListener = new ContextLoaderListener( // 리스너
            rootApplicationContext);
        servletContext.addListener(loaderListener);

        // servlet applicationContext
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(ServletConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        Dynamic servletRegistration = servletContext.addServlet("test", dispatcherServlet); // 서블릿추가
        servletRegistration.addMapping("/"); // 모든 요청이 dispatcherServlet이 하게된다
        servletRegistration.setLoadOnStartup(-1); //default = -1 필요할 때 로드 / 0이상 이면 서버가 뜰때 로드

    }
}
