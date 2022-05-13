package org.programmers.devcourse.voucher;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class EmbeddedDatabaseTestModule {

  private final static JdbcDatabaseContainer databaseContainer = new MySQLContainer<>(
      DockerImageName
          .parse("mysql:8.0.28-debian"))
      .withDatabaseName("order_mgmt")
      .withInitScript("init.sql")
      .withUsername("test")
      .withPassword("test")
      .withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci");
  public static final DataSource DATA_SOURCE = getDataSource();

  private EmbeddedDatabaseTestModule() {
  }

  private static DataSource getDataSource() {
    databaseContainer.start();
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .username(databaseContainer.getUsername())
        .password(databaseContainer.getPassword())
        .url(databaseContainer.getJdbcUrl())
        .build();
  }

}
