package org.prgrms.application.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.sql.DataSource;

public class ApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitializer.class);

    @EnableWebMvc
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.application.domain.voucher", "org.prgrms.application.repository.voucher","org.prgrms.application.service"}
    )
    static class RootConfig{
            @Bean
            public DataSource dataSource() {
                return new EmbeddedDatabaseBuilder()
                        .generateUniqueName(true)
                        .setType(EmbeddedDatabaseType.H2)
                        .setScriptEncoding("UTF-8")
                        .ignoreFailedDrops(true)
                        .addScript("schema.sql")
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
        logger.info("Starting Server...");
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(RootConfig.class);
        ContextLoaderListener loaderListener = new ContextLoaderListener(applicationContext);
        servletContext.addListener(loaderListener);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("test", dispatcherServlet);
        servletRegistration.addMapping("/*");
        servletRegistration.setLoadOnStartup(1);
    }
}
