package org.programmers.springbootbasic.wallet.repository;

import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.customer.repository.JdbcCustomerRepository;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.programmers.springbootbasic.wallet.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcWalletRepositoryTest {

    @Autowired
    JdbcWalletRepository jdbcWalletRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    void setup() {
        dbSetup();
    }

    @AfterEach
    void cleanup() {
        jdbcWalletRepository.deleteAll();
        jdbcVoucherRepository.deleteAll();
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("월렛을 추가 할 수 있다.")
    void testInsert() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L , LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), fixedAmountVoucher.getVoucherId());

        //when
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);
        var retrievedWallet = jdbcWalletRepository.findById(wallet.getWalletId());

        //then
        assertThat(retrievedWallet).isPresent().get().isEqualTo(wallet);
    }

    @Test
    @DisplayName("고객 아이디로 바우처를 조회 할 수 있다.")
    void testVoucherByCustomerIdFindAll() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L , LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        List<Voucher> voucherList = List.of(fixedAmountVoucher);
        jdbcVoucherRepository.insert(fixedAmountVoucher);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);

        //when
        jdbcWalletRepository.findVoucherByCustomerId(customer.getCustomerId());
        var vouchers = jdbcWalletRepository.findVoucherByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().hasSameElementsAs(voucherList);
    }

    @Test
    @DisplayName("바우처로 고객을 조회 할 수 있다.")
    void testCustomerByVoucherIdFindAll() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L , LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        List<Customer> customerList = List.of(customer);
        jdbcVoucherRepository.insert(voucher);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);

        //when
        jdbcWalletRepository.findCustomerByVoucherId(voucher.getVoucherId());
        var customers = jdbcWalletRepository.findCustomerByVoucherId(voucher.getVoucherId());

        //then
        assertThat(customers).usingRecursiveFieldByFieldElementComparator().hasSameElementsAs(customerList);
    }


    @Test
    @DisplayName("아이디로 월렛을 조회 할 수 있다.")
    void testFindById() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());

        //when
        jdbcVoucherRepository.insert(voucher);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);
        var findWallet = jdbcWalletRepository.findById(wallet.getWalletId());

        //then
        assertThat(findWallet).isPresent().get().isEqualTo(wallet);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 월렛을 조회 할 수 없다.")
    void testFindByNonexistentId() {
        //given
        var unknown = jdbcVoucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(unknown).isNotPresent();
    }

    @Test
    @DisplayName("아이디로 월렛을 삭제 할 수 있다.")
    void testDeleteById() {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());

        //when
        jdbcVoucherRepository.insert(voucher);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);
        jdbcWalletRepository.deleteById(wallet.getWalletId());
        var deletedWallet = jdbcWalletRepository.findById(wallet.getWalletId());

        //then
        assertThat(deletedWallet).isEmpty();
    }

    @Test
    @DisplayName("모든 월렛을 삭제 할 수 있다.")
    void testDeleteAll() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L, LocalDateTime.now());
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());
        Wallet wallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
        Wallet wallet1 = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId());
        Wallet wallet2 = new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher2.getVoucherId());

        //when
        jdbcVoucherRepository.insert(voucher);
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);
        jdbcCustomerRepository.insert(customer);
        jdbcWalletRepository.insert(wallet);
        jdbcWalletRepository.insert(wallet1);
        jdbcWalletRepository.insert(wallet2);
        jdbcWalletRepository.deleteAll();
        var emptyLists = jdbcWalletRepository.findVoucherByCustomerId(customer.getCustomerId());

        //then
        assertThat(emptyLists).isEmpty();
    }
}
