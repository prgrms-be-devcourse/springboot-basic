package org.prgrms.kdtspringdemo.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdtspringdemo.voucher",
        "org.prgrms.kdtspringdemo.customer",
        "org.prgrms.kdtspringdemo.configuration"
})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
    @Bean
    public DataSource dataSource() {
//          docker에 올린 DB로 시작하기(mysql v8.0.11)
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/customer_mgmt")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class)
                .build();
        return dataSource;
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}