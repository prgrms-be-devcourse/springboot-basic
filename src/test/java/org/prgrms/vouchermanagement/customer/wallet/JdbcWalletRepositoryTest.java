package org.prgrms.vouchermanagement.customer.wallet;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
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
class JdbcWalletRepositoryTest {

  @Configuration
  @ComponentScan(
    basePackages = {"org.prgrms.vouchermanagement.customer", "org.prgrms.vouchermanagement.voucher"}
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
  JdbcWalletRepository jdbcWalletRepository;

  @Autowired
  DataSource dataSource;

  EmbeddedMysql embeddedMysql;

  Customer customer1;
  Customer customer2;


  // Voucher Objects for Tests
  Voucher fixedAmountVoucher1;
  Voucher fixedAmountVoucher2;
  Voucher percentDiscountVoucher1;
  Voucher percentDiscountVoucher2;

  @BeforeAll
  void setUp() {
    // Objects for test
    customer1 = new Customer(UUID.randomUUID(), "customer1", "customer1@gmail.com", LocalDateTime.now());
    customer2 = new Customer(UUID.randomUUID(), "customer1", "customer1@gmail.com", LocalDateTime.now());
    fixedAmountVoucher1 = VoucherFactory.createVoucher(1, 100L, LocalDateTime.now());
    fixedAmountVoucher2 = VoucherFactory.createVoucher(1, 1000L, LocalDateTime.now());
    percentDiscountVoucher1 = VoucherFactory.createVoucher(2, 10L, LocalDateTime.now());
    percentDiscountVoucher2 = VoucherFactory.createVoucher(2, 20L, LocalDateTime.now());

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

  @BeforeEach
  void beforeEach() {
    jdbcWalletRepository.deleteAll();
  }

  @Test
  void testIssueVoucherToCustomer() {
    assertThat(jdbcWalletRepository.count(), is(0));

    jdbcWalletRepository.insert(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId());
    jdbcWalletRepository.insert(customer1.getCustomerId(), percentDiscountVoucher1.getVoucherId());
    jdbcWalletRepository.insert(customer2.getCustomerId(), fixedAmountVoucher2.getVoucherId());
    jdbcWalletRepository.insert(customer2.getCustomerId(), percentDiscountVoucher2.getVoucherId());

    assertThat(jdbcWalletRepository.count(), is(not(0)));
    assertThat(jdbcWalletRepository.count(), is(4));
  }

  @Test
  void testFindVouchersByCustomerId() {
    jdbcWalletRepository.insert(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId());
    jdbcWalletRepository.insert(customer1.getCustomerId(), percentDiscountVoucher1.getVoucherId());

    List<UUID> vouchers = jdbcWalletRepository.findVouchersByCustomerId(customer1.getCustomerId());
    assertThat(vouchers.size(), is(2));
  }

  @Test
  void testFindCustomersByVoucherId() {
    jdbcWalletRepository.insert(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId());
    List<UUID> customers = jdbcWalletRepository.findCustomersByVoucherId(fixedAmountVoucher1.getVoucherId());
    assertThat(customers.size(), is(1));
    assertThat(customers.contains(customer1.getCustomerId()), is(true));
  }

  @Test
  void testDelete() {
    jdbcWalletRepository.insert(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId());
    jdbcWalletRepository.insert(customer1.getCustomerId(), percentDiscountVoucher1.getVoucherId());
    jdbcWalletRepository.delete(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId());
    List<UUID> vouchers = jdbcWalletRepository.findVouchersByCustomerId(customer1.getCustomerId());
    assertThat(vouchers.size(), is(1));
  }
}