package com.kdt.commandLineApp;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages ="com.kdt.commandLineApp")
@EnableConfigurationProperties({AppProperties.class})
@PropertySource("application.yaml")
public class AppContext {
//    @Bean
//    public DataSource dataSource(AppProperties appProperties) {
//        return DataSourceBuilder.create()
//                .driverClassName(appProperties.getDb_driver_class())
//                .url(appProperties.getDb_url())
//                .username(appProperties.getDb_user())
//                .password(appProperties.getDb_pwd())
//                .type(MysqlDataSource.class)
//                .build();
//    }

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
}
