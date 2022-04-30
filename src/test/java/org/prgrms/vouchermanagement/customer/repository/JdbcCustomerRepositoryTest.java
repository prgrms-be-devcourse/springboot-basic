package org.prgrms.vouchermanagement.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import net.bytebuddy.implementation.bytecode.Duplication;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {
  @Configuration
  @ComponentScan(
    basePackages = {"org.prgrms.vouchermanagement.customer"}
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
  JdbcCustomerRepository jdbcCustomerRepository;

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
    customer2 = new Customer(UUID.randomUUID(), "customer2", "customer2@gmail.com", LocalDateTime.now());
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

  @AfterEach
  void afterEach() {
    jdbcCustomerRepository.deleteAll();
  }

  @Test
  @DisplayName("고객을 추가할 수 있다")
  public void testInsert() {
    jdbcCustomerRepository.insert(customer1);
    var retrieveCustomer = jdbcCustomerRepository.findById(customer1.getCustomerId());
    assertThat(retrieveCustomer.isEmpty(), is(false));
    assertThat(retrieveCustomer.get(), samePropertyValuesAs(customer1));
  }

  @Test
  @DisplayName("중복된 고객은 추가할 수 없다")
  public void testInsertException() {
    jdbcCustomerRepository.insert(customer1);
    assertThrows(DuplicateKeyException.class, () -> jdbcCustomerRepository.insert(customer1));
  }

  @Test
  @DisplayName("전체 고객을 조회할 수 있다")
  public void testFindAll() {
    jdbcCustomerRepository.insert(customer1);
    var customers = jdbcCustomerRepository.findAll();
    assertThat(customers.isEmpty(), is(false));
  }

  @Test
  @DisplayName("이름으로 고객을 조회할 수 있다")
  public void testFindByName() {
    jdbcCustomerRepository.insert(customer1);
    var customer = jdbcCustomerRepository.findByName(customer1.getName());
    assertThat(customer.isEmpty(), is(false));
    var unknown = jdbcCustomerRepository.findByName("unknown-user");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @DisplayName("이메일로 고객을 조회할 수 있다")
  public void testFindByEmail() {
    jdbcCustomerRepository.insert(customer1);
    var customer = jdbcCustomerRepository.findByEmail(customer1.getEmail().getAddress());
    assertThat(customer.isEmpty(), is(false));
    var unknown = jdbcCustomerRepository.findByEmail("unknown-user@gmail.com");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @DisplayName("고객을 수정할 수 있다")
  public void testUpdate() {
    jdbcCustomerRepository.insert(customer1);
    customer1.changeName("updated-user");
    jdbcCustomerRepository.update(customer1);

    var all = jdbcCustomerRepository.findAll();
    assertThat(all, hasSize(1));
    assertThat(all, everyItem(samePropertyValuesAs(customer1)));

    var retrievedCustomer = jdbcCustomerRepository.findById(customer1.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer1));
  }

  @Test
  @DisplayName("ID로 존재 유무를 알 수 있다")
  void testCheckExistenceById() {
    var result1 = jdbcCustomerRepository.checkExistenceById(customer1.getCustomerId());
    assertThat(result1, is(false));
    jdbcCustomerRepository.insert(customer1);
    var result2 = jdbcCustomerRepository.checkExistenceById(customer1.getCustomerId());
    assertThat(result2, is(true));
  }
}