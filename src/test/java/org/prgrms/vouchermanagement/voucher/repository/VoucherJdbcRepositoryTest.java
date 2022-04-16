package org.prgrms.vouchermanagement.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

  @Configuration
  @ComponentScan(
    basePackages = {"org.prgrms.vouchermanagement.voucher.repository"}
  )
  static class Config {
    @Bean
    public DataSource dataSource() {
      var dataSource = DataSourceBuilder.create()
        .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
        .username("test")
        .password("test1234!")
        .type(HikariDataSource.class)
        .build();
      return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
      return new NamedParameterJdbcTemplate(dataSource);
    }
  }

  @Autowired
  VoucherJdbcRepository voucherJdbcRepository;

  @Autowired
  DataSource dataSource;

  EmbeddedMysql embeddedMysql;

  Voucher voucher1 = VoucherFactory.createVoucher(1, 100L, LocalDateTime.now());
  Voucher voucher2 = VoucherFactory.createVoucher(2, 10L, LocalDateTime.now());

  @BeforeAll
  void setUp() {
    var mysqlConfig = aMysqldConfig(v8_0_11)
      .withCharset(UTF8)
      .withPort(2215)
      .withUser("test", "test1234!")
      .withTimeZone("Asia/Seoul")
      .build();
    embeddedMysql = anEmbeddedMysql(mysqlConfig)
      .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
      .start();
  }

  @AfterAll
  void cleanup() {
    embeddedMysql.stop();
  }

  @Test
  @Disabled
  @DisplayName("voucherJdbcRepository가 null이면 안 된다")
  void repositoryIsNotNull() {
    assertThat(voucherJdbcRepository, is(notNullValue()));
  }

  @Test
  @Order(1)
  @DisplayName("voucherJdbcRepository에 Voucher 인스턴스를 저장할 수 있다")
  void testInsert() {
    voucherJdbcRepository.insert(voucher1);
    assertThat(voucherJdbcRepository.count(), is(1));

    voucherJdbcRepository.insert(voucher2);
    assertThat(voucherJdbcRepository.count(), is(not(1)));
    assertThat(voucherJdbcRepository.count(), is(2));
  }


  @Test
  @Order(2)
  void testfindAll() {
    List<Voucher> dbVoucherList = voucherJdbcRepository.findAll();
    List<Voucher> voucherList = new ArrayList<>(){{
      add(voucher1);
      add(voucher2);
    }};



  }



  @Test
  @Order(3)
  @DisplayName("deleteAll로 모든 데이터를 삭제할 수 있다")
  void testDeleteAll() {
    // deleteAll을 test 하기 위해서는 db가 비어있으면 안 됨
    assertThat(voucherJdbcRepository.count(), is(not(0)));
    voucherJdbcRepository.deleteAll();
    assertThat(voucherJdbcRepository.count(), is(0));
  }
}