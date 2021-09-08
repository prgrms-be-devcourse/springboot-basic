package org.prgms.order.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.prgms.order.customer.repository.CustomerNamedJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

public class DatabaseInitializer {

    @Configuration
    @ComponentScan(basePackages = "org.prgms.order.customer")
    @EnableTransactionManagement
    static class RootConfig{

        @Value("${spring.datasource.url}")
        private String url;

        @Value("${spring.datasource.username}")
        private String username;

        @Value("${spring.datasource.password}")
        private String password;

        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }
    }
}
