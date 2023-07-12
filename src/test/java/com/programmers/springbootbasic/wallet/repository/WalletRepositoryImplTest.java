package com.programmers.springbootbasic.wallet.repository;

import com.programmers.springbootbasic.customer.domain.Customer;
import com.programmers.springbootbasic.customer.repository.JdbcCustomerRepository;
import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import com.programmers.springbootbasic.voucher.repository.VoucherRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletRepositoryImplTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.springbootbasic.voucher.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_test_db")
                    .username("admin")
                    .password("admin1234")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private DataSource dataSource;

    private VoucherRepository voucherRepository;
    private JdbcCustomerRepository jdbcCustomerRepository;
    private WalletRepositoryImpl walletRepositoryImpl;

    @BeforeEach
    void setUp() {
        voucherRepository = new JdbcVoucherRepository(dataSource);
        jdbcCustomerRepository = new JdbcCustomerRepository(dataSource);
        walletRepositoryImpl = new WalletRepositoryImpl(dataSource);
    }

    @AfterEach
    void after() {
        voucherRepository.deleteAll();
        jdbcCustomerRepository.deleteAll();
    }

    @DisplayName("특정 회원에게 바우처를 할당한다")
    @Test
    void updateVoucherCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "voucherName", 10L);
        Customer customer = new Customer("customerName");

        voucherRepository.save(fixedAmountVoucher);
        jdbcCustomerRepository.save(customer);

        //when
        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        UUID assignedCustomerId = walletRepositoryImpl.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId()).get().getCustomerId();
        //then
        assertThat(assignedCustomerId, is(customer.getCustomerId()));
    }

    @DisplayName("고객이 보유하고 있는 바우처를 조회한다")
    @Test
    void findVouchersByCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        PercentDiscountVoucher percentDiscountVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), "testName3", 40L);
        Customer customer = new Customer("customerName");

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);
        voucherRepository.save(percentDiscountVoucher2);
        jdbcCustomerRepository.save(customer);

        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        List<Voucher> vouchers = walletRepositoryImpl.findVouchersByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers.size(), is(2));
    }

    @DisplayName("특정 바우처를 보유한 고객을 조회한다")
    @Test
    void findCustomerByVoucherId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        Customer customer = new Customer("customerName");

        voucherRepository.save(fixedAmountVoucher);
        jdbcCustomerRepository.save(customer);
        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());

        //when
        Optional<Customer> resultCustomer = walletRepositoryImpl.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(resultCustomer.get().getCustomerId(), is(customer.getCustomerId()));
    }

    @DisplayName("특정 바우처를 보유한 고객을 찾을 수 없는 경우 빈 Customer를 반환한다")
    @Test
    void findCustomerByVoucherIdEmpty() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        voucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Customer> customer = walletRepositoryImpl.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId());

        //then
        org.assertj.core.api.Assertions.assertThat(customer).isEmpty();

    }

    @DisplayName("회원이 보유한 특정 바우처를 제거한다")
    @Test
    void deleteVoucherByVoucherIdAndCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        Customer customer = new Customer("customerName");

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);
        jdbcCustomerRepository.save(customer);

        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        walletRepositoryImpl.deleteVoucherByVoucherIdAndCustomerId(fixedAmountVoucher.getVoucherId(), customer.getCustomerId());
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result.size(), is(1));
    }

    @DisplayName("회원이 보유한 모든 바우처를 제거한다")
    @Test
    void deleteAllVouchersByCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        Customer customer = new Customer("customerName");

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);
        jdbcCustomerRepository.save(customer);

        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        walletRepositoryImpl.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        walletRepositoryImpl.deleteAllVouchersByCustomerId(customer.getCustomerId());
        List<Voucher> result = walletRepositoryImpl.findVouchersByCustomerId(customer.getCustomerId());

        //then
        org.assertj.core.api.Assertions.assertThat(result).isEmpty();
    }
}