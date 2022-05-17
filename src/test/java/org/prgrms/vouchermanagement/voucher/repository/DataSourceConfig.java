package org.prgrms.vouchermanagement.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


@Configuration
@ComponentScan(
  basePackages = {"org.prgrms.vouchermanagement.voucher.repository"}
)
public class DataSourceConfig {

//  @Bean
//  public DataSource dataSource() {
//    return DataSourceBuilder.create()
//      .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
//      .username("test")
//      .password("test1234!")
//      .type(HikariDataSource.class)
//      .build();
//  }
//
//  @Bean
//  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//    return new JdbcTemplate(dataSource);
//  }
//
//  @Bean
//  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
//    return new NamedParameterJdbcTemplate(dataSource);
//  }
}
