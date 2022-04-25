package org.prgms.management;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.management.blacklist", "org.prgms.management.io", "org.prgms.management.voucher",
        "org.prgms.management.customer", "org.prgms.management.wallet"})
@PropertySource("/application.yaml")
@EnableConfigurationProperties
public class AppConfiguration {
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
