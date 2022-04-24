package org.programs.kdt.Wallet;

import org.junit.jupiter.api.*;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerRepository;
import org.programs.kdt.Customer.JdbcCustomerRepository;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.repository.JdbcVoucherRepository;
import org.programs.kdt.Voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceTest {
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
                    .addScript("customers.sql")
                    .addScript("voucher.sql")
                    .addScript("wallet.sql")
                    .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        JdbcWalletRepository jdbcWalletRepository(JdbcTemplate jdbcTemplate) {
            return new JdbcWalletRepository(jdbcTemplate);
        }
        @Bean
        JdbcCustomerRepository customerRepository(JdbcTemplate jdbcTemplate){
            return new JdbcCustomerRepository(jdbcTemplate);
        }
        @Bean
        JdbcVoucherRepository jdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }
    }
    @Autowired
    WalletService walletService;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    Wallet wallet;
    Voucher voucher;
    Customer customer;

    @BeforeAll
    void setup() {
        VoucherType voucherType = VoucherType.PERCENT;
        voucher = voucherType.createVoucher(UUID.randomUUID(), 20L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customer = new Customer(UUID.randomUUID(), "choi", "choi@naver.com");
        voucherRepository.insert(voucher);
        customerRepository.insert(customer);
    }

    @Test
    @DisplayName("customer와 voucher를 통해 wallet을 생성할 수 있다.")
    @Order(1)
    public void creteWallet() {
        wallet = walletService.testCreate(customer.getCustomerId(), voucher.getVoucherId(), UUID.randomUUID());

        var retrieveWallet = walletService.findAll();

        assertThat(retrieveWallet.isEmpty(), is(false));
        assertThat(retrieveWallet, hasItem(samePropertyValuesAs(wallet)));
    }

    @Test
    @DisplayName("없는 voucherId로는 wallet를 만들지 못한다.")
    @Order(2)
    public void createWalletNotFoundVoucher() {
        VoucherType voucherType = VoucherType.PERCENT;
        Voucher anotherVoucher
                = voucherType.createVoucher(UUID.randomUUID(), 20L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        assertThrows(EntityNotFoundException.class, () -> walletService.testCreate(customer.getCustomerId(), anotherVoucher.getVoucherId(), UUID.randomUUID()));
    }

    @Test
    @DisplayName("없는 customerId로는 wallet를 만들지 못한다.")
    @Order(3)
    public void createWalletNotFoundCusomer() {
        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi", "choi@naver.com");

        assertThrows(EntityNotFoundException.class, () -> walletService.testCreate(anotherCustomer.getCustomerId(), voucher.getVoucherId(), UUID.randomUUID()));
    }

    @Test
    @DisplayName("똑같은 UUID의 wallet를 만들지 못한다.")
    @Order(4)
    public void createWalletDuplicate() {
        assertThrows(DuplicationException.class, () -> walletService.testCreate(wallet.getCustomerId(), wallet.getVoucherId(), wallet.getWalletId()));
    }

    @Test
    @DisplayName("전체 바우처 지갑을 조회할 수 있다.")
    @Order(5)
    void findAll() {
        List<Wallet> walletList = walletService.findAll();
        assertThat(walletList.isEmpty(), is(false));
        assertThat(walletList, hasSize(1));
        assertThat(walletList, everyItem(samePropertyValuesAs(wallet)));
    }

    @Test
    @DisplayName("바우처 id로 wallet을 조회할 수 있다.")
    @Order(6)
    void findByVoucherId() {

        VoucherType voucherType = VoucherType.FIXEDAMOUNT;
        Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now());
        voucherRepository.insert(anotherVoucher);

        Wallet newWallet = walletService.testCreate(customer.getCustomerId(), anotherVoucher.getVoucherId(), UUID.randomUUID());
        List<Wallet> retrieveWalletList = walletService.findByVoucherId(voucher.getVoucherId());

        assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
        assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
    }
    @Test
    @DisplayName("없는 바우처 id로 empty값을 반환한다.")
    @Order(7)
    void findByVoucherIdError() {

        VoucherType voucherType = VoucherType.FIXEDAMOUNT;
        Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        Wallet newWallet = new Wallet(anotherVoucher, customer, UUID.randomUUID(), LocalDateTime.now());

        List<Wallet> retrieveWalletList = walletService.findByVoucherId(anotherVoucher.getVoucherId());
        assertThat(retrieveWalletList.isEmpty(), is(true));
    }

    @Test
    @DisplayName("유저id로 wallet을 조회할 수 있다.")
    @Order(8)
    void findByCustomerId() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi2@naver.com",LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerRepository.insert(anotherCustomer);

        Wallet newWallet = walletService.testCreate(anotherCustomer.getCustomerId(), voucher.getVoucherId(), UUID.randomUUID());
        List<Wallet> retrieveWalletList =walletService.findByCustomerId(wallet.getCustomerId());

        assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
        assertThat(retrieveWalletList, not(hasItem(newWallet)));
    }

    @Test
    @DisplayName("없는 유저id로 wallet을 조회하면 빈리스트를 반환한다.")
    @Order(9)
    void findByCustomerIdError() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi2@naver.com");
        List<Wallet> retrieveWalletList = walletService.findByCustomerId(anotherCustomer.getCustomerId());
        assertThat(retrieveWalletList.isEmpty(), is(true));
    }

    @Test
    @DisplayName("없는 wallet id로 wallet을 조회할 수 없다.")
    @Order(9)
    void findByIdError() {
        Wallet newWallet = new Wallet(voucher, customer, UUID.randomUUID(), LocalDateTime.now());
        assertThrows(EntityNotFoundException.class,() -> walletService.findByWalletId(newWallet.getWalletId()));
    }

    @Test
    @DisplayName("email로 wallet을 조회할 수 있다.")
    @Order(10)
    void findByCustomerEmail() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi2@naver.com");
        Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());

        List<Wallet> retrieveWalletList =
                walletService.findByCustomerEmail(wallet.getCustomerEmail());

        assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(wallet)));
        assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
    }

    @Test
    @DisplayName("없는 email로 wallet을 조회하면 빈 리스트")
    @Order(11)
    void findByCustomerEmailError() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi23@naver.com");
        assertThat(walletService.findByCustomerEmail(anotherCustomer.getEmail()), hasSize(0));
    }

    @Test
    @DisplayName("유저id로 wallet을 지울 수 있다.")
    @Order(12)
    void deleteByCustomerId() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi12122@naver.com");
        customerRepository.insert(anotherCustomer);

        Wallet newWallet = walletService.testCreate(anotherCustomer.getCustomerId(), voucher.getVoucherId(), UUID.randomUUID());
        walletService.deleteByCustomerId(newWallet.getCustomerId());

        List<Wallet> retrieveWalletList = walletService.findAll();

        assertThat(this.wallet.getCustomerId(), not(newWallet.getCustomerId()));
        assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(this.wallet)));
        assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
    }

    @Test
    @DisplayName("없는 유저id로 wallet을 지울 수 없다.")
    @Order(13)
    void deleteByCustomerIdError() {

        Customer anotherCustomer = new Customer(UUID.randomUUID(), "choi2", "choi2@naver.com");
        Wallet newWallet = new Wallet(voucher, anotherCustomer, UUID.randomUUID(), LocalDateTime.now());

        assertThrows(EntityNotFoundException.class, () ->walletService.deleteByCustomerId(newWallet.getCustomerId()));
    }



    @Test
    @DisplayName("uuid로 wallet을 지울 수 있다.")
    @Order(16)
    void deleteById() {
        VoucherType voucherType = VoucherType.FIXEDAMOUNT;
        Voucher anotherVoucher = voucherType.createVoucher(UUID.randomUUID(), 21L, LocalDateTime.now());
        voucherRepository.insert(anotherVoucher);
        Wallet newWallet = walletService.testCreate(customer.getCustomerId(), anotherVoucher.getVoucherId(), UUID.randomUUID());
        walletService.deleteById(newWallet.getWalletId());
        List<Wallet> retrieveWalletList = walletService.findAll();

        assertThat(retrieveWalletList, not(hasItem(samePropertyValuesAs(newWallet))));
        assertThat(retrieveWalletList, hasItem(samePropertyValuesAs(this.wallet)));
    }
}