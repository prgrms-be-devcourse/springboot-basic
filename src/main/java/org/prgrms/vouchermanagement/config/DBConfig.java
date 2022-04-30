package org.prgrms.vouchermanagement.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
  @Bean
  public DataSource dataSource() {
    var dataSource = DataSourceBuilder.create()
      .url("jdbc:mysql://localhost:3306/voucher_mgmt")
      .username("root")
      .password("root1234!")
      .type(HikariDataSource.class)
      .build();
    return dataSource;
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }
}
