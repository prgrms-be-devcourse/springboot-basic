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


}
