package org.prgrms.kdt.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdt.domain","org.prgrms.kdt.jdbcRepository",
                        "org.prgrms.kdt.repository"}
)
public class AppConfig {

    @Bean
    public DataSource dataSource(){
        var datasource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
                .build();
        datasource.setMaximumPoolSize(100);
        datasource.setMinimumIdle(100);
        return datasource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

}
