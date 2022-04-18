package org.programmers.devcourse.voucher;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.programmers.devcourse.voucher.configuration.JdbcProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class EmbeddedDatabaseTestModule {

  protected final static MySQLContainer mysql = new MySQLContainer<>(
      DockerImageName
          .parse("mysql:8.0.28-debian"))
      .withDatabaseName("order_mgmt")
      .withInitScript("init.sql")
      .withUsername("test")
      .withPassword("test")
      .withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci");
  protected final static JdbcProperties jdbcProperties = new JdbcProperties() {
    @Override
    public String getUser() {
      return mysql.getUsername();
    }

    @Override
    public String getPassword() {
      return mysql.getPassword();
    }

    @Override
    public String getUrl() {
      return mysql.getJdbcUrl();
    }

  };

  private static DataSource testDataSource = null;

  protected final static DataSource getTestDataSource() {
    if (testDataSource == null) {
      testDataSource = DataSourceBuilder.create().type(HikariDataSource.class)
          .username(jdbcProperties.getUser()).password(jdbcProperties.getPassword())
          .url(jdbcProperties.getUrl())
          .build();
    }

    return testDataSource;
  }

}
