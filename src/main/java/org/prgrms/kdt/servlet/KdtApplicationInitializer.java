//package org.prgrms.kdt.servlet;
//
//
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.zaxxer.hikari.HikariDataSource;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import org.prgrms.kdt.controller.VoucherController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.*;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.oxm.xstream.XStreamMarshaller;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.config.annotation.*;
//import org.springframework.web.servlet.resource.EncodedResourceResolver;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring6.view.ThymeleafViewResolver;
//
//import javax.sql.DataSource;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class KdtApplicationInitializer implements WebApplicationInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(KdtApplicationInitializer.class);
//
//    @Configuration
//    @EnableWebMvc
//    @ComponentScan(basePackages = "org.prgrms.kdt",
//            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherController.class),
//            useDefaultFilters = false
//    )
//    static class ServletConfig implements WebMvcConfigurer, ApplicationContextAware {
//
//        ApplicationContext applicationContext;
//
//        @Override
//        public void configureViewResolvers(ViewResolverRegistry registry) {
//            registry.jsp().viewNames("jsp/*");
//
//            var springResourceTemplateResolver = new SpringResourceTemplateResolver();
//            springResourceTemplateResolver.setApplicationContext(applicationContext);
//            springResourceTemplateResolver.setPrefix("/WEB-INF/");
//            springResourceTemplateResolver.setSuffix(".html");
//            var springTemplateEngine = new SpringTemplateEngine();
//            springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);
//
//            var thymeleafViewResolver = new ThymeleafViewResolver();
//            thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
//            thymeleafViewResolver.setOrder(1);
//            thymeleafViewResolver.setViewNames(new String[]{"views/*"});
//            registry.viewResolver(thymeleafViewResolver);
//        }
//
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/resources/**")
//                    .addResourceLocations("/resources/")
//                    .setCachePeriod(60)
//                    .resourceChain(true)
//                    .addResolver(new EncodedResourceResolver());
//        }
//
//        @Override
//        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//            this.applicationContext = applicationContext;
//        }
//
//        @Override
//        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//            var messageConverter = new MarshallingHttpMessageConverter();
//            var xStreamMarshaller = new XStreamMarshaller();
//            messageConverter.setMarshaller(xStreamMarshaller);
//            messageConverter.setUnmarshaller(xStreamMarshaller);
//            converters.add(0, messageConverter);
//
//            var javaTimeModule = new JavaTimeModule();
//            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
//            var modules = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule);
//            converters.add(1, new MappingJackson2HttpMessageConverter(modules.build()));
//        }
//
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/api/**")
//                    .allowedMethods("GET", "POST")
//                    .allowedOrigins("*");
//        }
//    }
//
//    @Configuration
//    @ComponentScan(basePackages = "org.prgrms.kdt",
//            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherController.class)
//    )
//    @EnableTransactionManagement
//    static class RootConfig {
//
//        @Bean
//        public DataSource dataSource() {
//            var dataSource = DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost/order_mgmt")
//                    .username("root")
//                    .password("root1234!")
//                    .type(HikariDataSource.class)
//                    .build();
//            dataSource.setMaximumPoolSize(1000);
//            dataSource.setMinimumIdle(100);
//            return dataSource;
//        }
//
//
//        @Bean
//        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//            return new JdbcTemplate(dataSource);
//        }
//
//        @Bean
//        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
//            return new NamedParameterJdbcTemplate(jdbcTemplate);
//        }
//
//        @Bean
//        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
//            return new DataSourceTransactionManager(dataSource);
//        }
//
//    }
//
//    @Override
//    public void onStartup(ServletContext servletContext) {
//        logger.info("Staring Server...");
//        var rootApplicationContext = new AnnotationConfigWebApplicationContext();
//        rootApplicationContext.register(RootConfig.class);
//        var loaderListener = new ContextLoaderListener(rootApplicationContext);
//        servletContext.addListener(loaderListener);
//
//        var applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(ServletConfig.class);
//        var dispatcherServlet = new DispatcherServlet(applicationContext);
//        var servletRegistration = servletContext.addServlet("test", dispatcherServlet);
//        servletRegistration.addMapping("/");
//        servletRegistration.setLoadOnStartup(1);
//    }
//
//}
