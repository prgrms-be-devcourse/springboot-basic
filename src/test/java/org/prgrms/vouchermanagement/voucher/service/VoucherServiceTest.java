package org.prgrms.vouchermanagement.voucher.service;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
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
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

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
  DataSource dataSource;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  VoucherRepository voucherRepository;

  @Autowired
  VoucherService voucherService;

  EmbeddedMysql embeddedMysql;

  Customer customer1;
  Customer customer2;

  Voucher fixedAmountVoucher1;
  Voucher percentDiscountVoucher1;

  @BeforeAll
  void setUp() {
    // Objects for test
    customer1 = new Customer(UUID.randomUUID(), "customer1", "customer1@gmail.com", LocalDateTime.now());
    customer2 = new Customer(UUID.randomUUID(), "customer2", "customer2@gmail.com", LocalDateTime.now());
    fixedAmountVoucher1 = VoucherFactory.createVoucher(1, 100L, LocalDateTime.now());
    percentDiscountVoucher1 = VoucherFactory.createVoucher(2, 10L, LocalDateTime.now());


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

  @AfterEach
  void afterEach() {
    customerRepository.deleteAll();
    voucherRepository.deleteAll();
  }

  @Test
  void testIssueVoucher() {
    customerRepository.insert(customer1);
    voucherRepository.insert(fixedAmountVoucher1);
    assertThat(voucherService.issueVoucher(customer1.getCustomerId(), fixedAmountVoucher1.getVoucherId()), is(true));
  }

  @Test
  void testIssueVoucherFalseWhenCustomerDoesNotExist() {
    customerRepository.insert(customer1);
    voucherRepository.insert(fixedAmountVoucher1);
    assertThat(voucherService.issueVoucher(customer2.getCustomerId(), fixedAmountVoucher1.getVoucherId()), is(false));
  }

  @Test
  void testIssueVoucherFalseWhenVoucherDoesNotExist() {
    customerRepository.insert(customer1);
    voucherRepository.insert(fixedAmountVoucher1);
    assertThat(voucherService.issueVoucher(customer1.getCustomerId(), percentDiscountVoucher1.getVoucherId()), is(false));
  }
}