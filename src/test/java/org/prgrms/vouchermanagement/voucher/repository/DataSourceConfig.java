package org.prgrms.vouchermanagement.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  @Primary
  @Bean("masterDateSource")
  @ConfigurationProperties(prefix="spring.datasource.master.hikari")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create()
      .url("jdbc:mysql://localhost:3306/voucher_mgmt")
      .username("root")
      .password("root1234!")
      .type(HikariDataSource.class)
      .build();
  }
}
