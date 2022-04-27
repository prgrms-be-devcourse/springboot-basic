package org.prgrms.vouchermanagement.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

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
  JdbcVoucherRepository jdbcVoucherRepository;

  @Autowired
  DataSource dataSource;

  EmbeddedMysql embeddedMysql;

  // Voucher Objects for Tests
  Voucher fixedAmountVoucher1 = VoucherFactory.createVoucher(1, 100L, LocalDateTime.now());
  Voucher fixedAmountVoucher2 = VoucherFactory.createVoucher(1, 1000L, LocalDateTime.now());
  Voucher percentDiscountVoucher1 = VoucherFactory.createVoucher(2, 10L, LocalDateTime.now());
  Voucher percentDiscountVoucher2 = VoucherFactory.createVoucher(2, 20L, LocalDateTime.now());

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
    assertThat(jdbcVoucherRepository, is(notNullValue()));
  }

  @Test
  @Order(1)
  @DisplayName("voucherJdbcRepository에 Voucher 인스턴스를 저장할 수 있다")
  void testInsert() {
    jdbcVoucherRepository.insert(fixedAmountVoucher1);
    assertThat(jdbcVoucherRepository.count(), is(1));

    jdbcVoucherRepository.insert(percentDiscountVoucher1);
    assertThat(jdbcVoucherRepository.count(), is(not(1)));
    assertThat(jdbcVoucherRepository.count(), is(2));
  }


  @Test
  @Order(2)
  void testfindAll() {
    List<Voucher> dbVoucherList = jdbcVoucherRepository.findAll();
    assertThat(dbVoucherList.size(), is(2));
  }

  @Test
  @Order(3)
  @DisplayName("Voucher의 타입을 사용하여 voucher을 조회할 수 있다")
  void testFindByVoucherType() {
    jdbcVoucherRepository.insert(fixedAmountVoucher2);
    List<Voucher> fixedAmountVouchers = jdbcVoucherRepository.findByVoucherType(VoucherType.FIXED_AMOUNT);
    assertThat(fixedAmountVouchers.size(), is(2));

    jdbcVoucherRepository.insert(percentDiscountVoucher2);
    List<Voucher> percentDiscountVouchers = jdbcVoucherRepository.findByVoucherType(VoucherType.PERCENT_DISCOUNT);
    assertThat(percentDiscountVouchers.size(), is(2));
  }

  @Test
  @Order(4)
  @DisplayName("voucherId를 사용하여 voucher를 조회할 수 있다")
  void testByVoucherId() {
    assertThat(jdbcVoucherRepository.findById(fixedAmountVoucher1.getVoucherID()), is(not(Optional.empty())));
    assertThat(jdbcVoucherRepository.findById(UUID.randomUUID()), is(Optional.empty()));
  }

  @Test
  @Order(5)
  @DisplayName("voucherId를 사용하여 reduction을 수정할 수 있다")
  void testUpdateById() {
    assertThat(jdbcVoucherRepository.findById(fixedAmountVoucher1.getVoucherID()).get().getReduction(), is(100L));
    jdbcVoucherRepository.updateById(fixedAmountVoucher1.getVoucherID(), 200L);
    assertThat(jdbcVoucherRepository.findById(fixedAmountVoucher1.getVoucherID()).get().getReduction(), is(200L));
  }

  @Test
  @Order(6)
  void testDeleteById() {
    jdbcVoucherRepository.deleteById(fixedAmountVoucher1.getVoucherID());
    Optional<Voucher> foundVoucher = jdbcVoucherRepository.findById(fixedAmountVoucher1.getVoucherID());
    assertThat(foundVoucher.isPresent(), is(false));
  }

  @Test
  @Order(7)
  @DisplayName("deleteAll로 모든 데이터를 삭제할 수 있다")
  void testDeleteAll() {
    // deleteAll을 test 하기 위해서는 db가 비어있으면 안 됨
    assertThat(jdbcVoucherRepository.count(), is(not(0)));
    jdbcVoucherRepository.deleteAll();
    assertThat(jdbcVoucherRepository.count(), is(0));
  }
}