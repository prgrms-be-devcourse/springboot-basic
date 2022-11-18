package org.prgrms.memory;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
public class JdbcBase {

  @Configuration
  @ComponentScan(basePackages = {"org.prgrms.memory"})
  static class Config {

    @Bean
    public DataSource dataSource() {
      return DataSourceBuilder.create()
          .url("jdbc:mysql://localhost:10000/voucher_app")
          .username("test")
          .password("test1234!")
          .type(HikariDataSource.class)
          .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

  }


  @Autowired
  DataSource dataSource;

  EmbeddedMysql embeddedMysql;

  @BeforeAll
  void setup() {
    var mysqlConfig = aMysqldConfig(v8_0_11)
        .withCharset(UTF8)
        .withPort(10000)
        .withUser("test", "test1234!")
        .withTimeZone("Asia/Seoul")
        .build();

    embeddedMysql = anEmbeddedMysql(mysqlConfig)
        .addSchema("voucher_app", classPathScript("schema.sql"))
        .start();
  }

  @AfterAll
  void cleanup() {
    embeddedMysql.stop();
  }

}
