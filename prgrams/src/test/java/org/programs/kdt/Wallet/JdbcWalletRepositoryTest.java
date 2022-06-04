package org.programs.kdt.Wallet;

import org.junit.jupiter.api.*;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.JdbcCustomerRepository;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("db")
class JdbcWalletRepositoryTest {

  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt"})
  static class Config {
    @Bean
    public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .generateUniqueName(true)
          .setType(H2)
          .setScriptEncoding("UTF-8")
          .ignoreFailedDrops(true)
          .addScript("wallet.sql")
          .addScript("voucher.sql")
          .addScript("customers.sql")
          .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

    @Bean
    JdbcWalletRepository walletRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcWalletRepository(jdbcTemplate);
    }

    @Bean
    JdbcCustomerRepository customerRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcCustomerRepository(jdbcTemplate);
    }

    @Bean
    JdbcVoucherRepository voucherRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcVoucherRepository(jdbcTemplate);
    }
  }

  @Autowired JdbcWalletRepository walletRepository;
  @Autowired JdbcVoucherRepository voucherRepository;
  @Autowired JdbcCustomerRepository customerRepository;

  Wallet wallet;
  Voucher voucher;
  Customer customer;

  @BeforeAll
  void setup() {
    walletRepository.deleteAll();
    VoucherType voucherType = VoucherType.PERCENT;
    voucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 20L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    customer = new Customer(UUID.randomUUID(), "choi", "choi@naver.com");
    wallet =
        new Wallet(
            voucher,
            customer,
            UUID.randomUUID(),
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    voucherRepository.insert(voucher);
    customerRepository.insert(customer);
  }

  @Test
  @DisplayName("wallet을 추가할 수 있다.")
  @Order(1)
  void insert() {
    walletRepository.insert(wallet);

    var retrieveWallet = walletRepository.findById(wallet.getWalletId());

    assertThat(retrieveWallet.isEmpty(), is(false));
    assertThat(wallet, samePropertyValuesAs(retrieveWallet.get()));
  }

  @Test
  @DisplayName("같은 uuid를 가진 wallet을 추가할 수 없다.")
  @Order(2)
  void insertError() {

    assertThrows(DuplicateKeyException.class, () -> walletRepository.insert(wallet));
  }

  @Test
  @DisplayName("전체 바우처 지갑을 조회할 수 있다.")
  @Order(3)
  void findAll() {
    List<Wallet> walletList = walletRepository.findAll();
    assertThat(walletList.isEmpty(), is(false));
    assertThat(walletList, hasSize(1));
    assertThat(walletList, everyItem(samePropertyValuesAs(wallet)));
  }

  @Test
  @DisplayName("바우처 id로 wallet을 조회할 수 있다.")
  @Order(4)
  void findByVoucherId() {

    VoucherType voucherType = VoucherType.FIXEDAMOUNT;
    Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now());
    voucherRepository.insert(anotherVoucher);

    Wallet newWallet = new Wallet(anotherVoucher, customer, UUID.randomUUID(), LocalDateTime.now());
    walletRepository.insert(newWallet);
    List<Wallet> retrieveWalletList = walletRepository.findByVoucherId(voucher.getVoucherId());

    assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
    assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
  }

  @Test
  @DisplayName("유저id로 wallet을 조회할 수 있다.")
  @Order(5)
  void findByCustomerId() {

    Customer anotherCustomer =
        new Customer(
            UUID.randomUUID(),
            "choi2",
            "choi2@naver.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    customerRepository.insert(anotherCustomer);
    Wallet newWallet =
        new Wallet(
            voucher,
            anotherCustomer,
            UUID.randomUUID(),
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    walletRepository.insert(newWallet);
    List<Wallet> retrieveWalletList = walletRepository.findByCustomerId(wallet.getCustomerId());

    assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
    assertThat(retrieveWalletList, not(hasItem(newWallet)));
  }

  @Test
  @DisplayName("wallet id로 wallet을 조회할 수 있다.")
  @Order(6)
  void findById() {
    Optional<Wallet> retrieveWallet = walletRepository.findById(wallet.getWalletId());

    assertThat(retrieveWallet.isEmpty(), is(false));
    assertThat(retrieveWallet.get(), samePropertyValuesAs(wallet));
  }

  @Test
  @DisplayName("없는 바우처 id로 empty값을 반환한다.")
  @Order(7)
  void findByVoucherIdError() {

    VoucherType voucherType = VoucherType.FIXEDAMOUNT;
    Voucher anotherVoucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 21L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    Wallet newWallet = new Wallet(anotherVoucher, customer, UUID.randomUUID(), LocalDateTime.now());

    List<Wallet> retrieveWalletList =
        walletRepository.findByVoucherId(anotherVoucher.getVoucherId());
    assertThat(retrieveWalletList.isEmpty(), is(true));
  }

  @Test
  @DisplayName("없는 유저id로 wallet을 조회하면 empty값을 반환한다")
  @Order(8)
  void findByCustomerIdError() {

    Customer anotherCustomer =
        new Customer(UUID.randomUUID(), "choi3", "choi3@naver.com");
    Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());
    List<Wallet> retrieveWalletList =
        walletRepository.findByCustomerId(anotherCustomer.getCustomerId());
    assertThat(retrieveWalletList.isEmpty(), is(true));
  }

  @Test
  @DisplayName("없는 wallet id로 wallet을 조회할 수 없다.")
  @Order(9)
  void findByIdError() {
    Wallet newWallet = new Wallet(voucher, customer, UUID.randomUUID(), LocalDateTime.now());
    Optional<Wallet> retrieveWallet = walletRepository.findById(newWallet.getWalletId());

    assertThat(retrieveWallet.isEmpty(), is(true));
  }

  @Test
  @DisplayName("email로 wallet을 조회할 수 있다.")
  @Order(10)
  void findByCustomerEmail() {

    Customer anotherCustomer =
        new Customer(UUID.randomUUID(), "choi2322", "chi223@naver.com");
    customerRepository.insert(anotherCustomer);

    Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    walletRepository.insert(newWallet);
    List<Wallet> retrieveWalletList =
        walletRepository.findByCustomerEmail(wallet.getCustomerEmail());

    assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
    assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
  }

  @Test
  @DisplayName("없는 email로 wallet을 조회하면 empty를 반환한다.")
  @Order(11)
  void findByCustomerEmailError() {

    Customer anotherCustomer =
        new Customer(UUID.randomUUID(), "choi4", "choi2223@naver.com");
    Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());
    List<Wallet> retrieveWalletList =
        walletRepository.findByCustomerEmail(anotherCustomer.getEmail());
    assertThat(retrieveWalletList.isEmpty(), is(true));
  }

  @Test
  @DisplayName("유저id로 wallet을 지울 수 있다.")
  @Order(12)
  void deleteByCustomerId() {

    Customer anotherCustomer =
        new Customer(UUID.randomUUID(), "choi7", "choi2271@naver.com");
    customerRepository.insert(anotherCustomer);
    Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());
    walletRepository.insert(newWallet);
    walletRepository.deleteByCustomerId(newWallet.getCustomerId());

    List<Wallet> retrieveWalletList = walletRepository.findAll();

    assertThat(wallet.getCustomerId(), not(newWallet.getCustomerId()));
    assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
    assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
  }

  @Test
  @DisplayName("없는 유저id로 wallet을 지울 수 없다.")
  @Order(13)
  void deleteByCustomerIdError() {

    Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi28", "choi28@naver.com");
    Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());

    walletRepository.deleteByCustomerId(newWallet.getCustomerId());
  }

  @Test
  @DisplayName("uuid로 wallet을 지울 수 있다.")
  @Order(16)
  void deleteById() {
    VoucherType voucherType = VoucherType.FIXEDAMOUNT;
    Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now());
    Wallet newWallet = new Wallet(anotherVoucher, customer, UUID.randomUUID(), LocalDateTime.now());

    walletRepository.insert(newWallet);
    walletRepository.deleteById(newWallet.getWalletId());
    List<Wallet> retrieveWalletList = walletRepository.findAll();

    assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
    assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
  }

  @Test
  @DisplayName("db테이블을 초기화할 수 있다.")
  @Order(18)
  void deleteAll() {
    VoucherType voucherType = VoucherType.FIXEDAMOUNT;
    Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now());
    Wallet newWallet = new Wallet(anotherVoucher, customer, UUID.randomUUID(), LocalDateTime.now());

    walletRepository.insert(newWallet);
    walletRepository.deleteAll();
    List<Wallet> retrieveWalletList = walletRepository.findAll();

    assertThat(retrieveWalletList, hasSize(0));
  }
}
