package org.programmers.devcourse.voucher.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcConfiguration {

  @Bean
  DataSource dataSource(JdbcProperties jdbcProperties) {
    var dataSource = DataSourceBuilder.create().type(HikariDataSource.class)
        .username(jdbcProperties.getUser()).password(jdbcProperties.getPassword())
        .url(jdbcProperties.getUrl())
        .build();
    return dataSource;
  }
}
