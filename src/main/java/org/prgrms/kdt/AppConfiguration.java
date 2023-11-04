package org.prgrms.kdt;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
  basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order", "org.prgrms.kdt.io"}
)
@PropertySource(value = "application.yaml")
@EnableConfigurationProperties
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/order_mgmt")
                .username("root")
                .password("110811")
                .type(HikariDataSource.class)
                .build();
        return dataSource;
    }
}
