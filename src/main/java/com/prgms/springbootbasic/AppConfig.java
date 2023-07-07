package com.prgms.springbootbasic;

import com.prgms.springbootbasic.util.YamlPropertyFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@PropertySource(value = "application.yaml", factory = YamlPropertyFactory.class)
public class AppConfig {

    @Bean
    public DataSource dataSource(@Value("${database.url}") String url,
                                 @Value("${database.hostname}") String hostname,
                                 @Value("${database.password}") String password) {
        System.out.println(url);
        HikariDataSource datasource = DataSourceBuilder.create()
                .url(url)
                .username(hostname)
                .password(password)
                .type(HikariDataSource.class)
                .build();
        return datasource;
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
