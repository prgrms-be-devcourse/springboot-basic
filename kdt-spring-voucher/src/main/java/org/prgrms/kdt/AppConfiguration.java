package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.customer.repository.CustomerJdbcRepository;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class AppConfiguration {
    @Autowired
    Environment environment;
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(environment.getProperty("mysql.url"))
            .username(environment.getProperty("mysql.username"))
            .password(environment.getProperty("mysql.password"))
            .type(HikariDataSource.class)
            .build();
    }
    @Bean
    public CustomerRepository customerRepository(DataSource dataSource) {
        return new CustomerJdbcRepository(dataSource());
    }
}
