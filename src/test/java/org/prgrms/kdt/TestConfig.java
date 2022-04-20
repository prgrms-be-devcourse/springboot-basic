package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(
        basePackages = {"org.prgrms.kdt"}
)
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test")
                .username("park")
                .password("1234")
                .type(HikariDataSource.class)
                .build();
    }
}
