package org.prgrms.kdt.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher",
    "org.prgrms.kdt.controller",
    "org.prgrms.kdt.customer"})
@EnableConfigurationProperties
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class VoucherConfig {

    @Bean
    public DataSource dataSource() {
       return  DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/voucher_mgmt")
            .username("root")
            .password("asd1234!")
            .build();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
