package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.prgrms.vouchermanager.repository.wallet.WalletRepository;
import com.prgrms.vouchermanager.service.WalletService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class WalletControllerTest {

    private WalletRepository repository;
    private WalletService service;
    private WalletController controller;
    private VoucherJdbcRepository voucherJdbcRepository;
    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate template;

    Customer customer1 = new Customer("푸리나", 1993);
    Voucher voucher1 = new FixedAmountVoucher(20000);
    Voucher voucher2 = new PercentAmountVoucher(20);
    Wallet wallet1 = new Wallet(voucher1.getId(), customer1.getId());

    private final static String DELETE_WALLETS_QUERY = "delete from wallets;";
    private final static String DELETE_CUSTOMERS_QUERY = "delete from customers;";
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    @Configuration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                    .username("root")
                    .password("suzzingV1999@")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @BeforeEach
    void beforeEach() {
        repository = new WalletRepository(template);
        voucherJdbcRepository = new VoucherJdbcRepository(template.getDataSource());
        customerRepository = new CustomerRepository(template.getDataSource(), new BlacklistFileRepository("src/main/resources/customer_blacklist.csv"));
        service = new WalletService(repository, customerRepository, voucherJdbcRepository);
        controller = new WalletController(service);

        voucherJdbcRepository.create(voucher1);
        voucherJdbcRepository.create(voucher2);
        customerRepository.create(customer1);

        repository.create(wallet1);
    }

    @AfterEach
    void afterEach() {
        template.execute(DELETE_WALLETS_QUERY);
        template.execute(DELETE_CUSTOMERS_QUERY);
        template.execute(DELETE_VOUCHERS_QUERY);
    }

    @Test
    @DisplayName("create")
    void create() {
        Wallet createWallet = controller.create(customer1.getId(), voucher2.getId());

        Assertions.assertThat(createWallet.getVoucherId()).isEqualTo(voucher2.getId());
        Assertions.assertThat(createWallet.getCustomerId()).isEqualTo(customer1.getId());
    }

    @Test
    @DisplayName("findByCustomerId")
    void findByCustomerId() {
        Wallet createWallet = controller.create(customer1.getId(), voucher2.getId());
        List<Voucher> voucherList = controller.findByCustomerId(createWallet.getCustomerId());

        Assertions.assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByVoucherId")
    void findByVoucherId() {
        Wallet createWallet = controller.create(customer1.getId(), voucher2.getId());
        List<Customer> customerList = controller.findByVoucherId(createWallet.getVoucherId());

        Assertions.assertThat(customerList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = controller.delete(wallet1.getCustomerId(), wallet1.getVoucherId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
