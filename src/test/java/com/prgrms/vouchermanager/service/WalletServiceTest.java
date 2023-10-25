package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.prgrms.vouchermanager.repository.wallet.WalletRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class WalletServiceTest {
    private WalletRepository repository;
    private WalletService service;
    private VoucherJdbcRepository voucherJdbcRepository;
    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate template;
    Wallet wallet1 = new Wallet(UUID.randomUUID(), UUID.fromString("c80f7d69-5033-423c-b7d2-a11e7ee936dd"), UUID.fromString("70754a2f-d87d-4f69-af71-1d4bfe855e28"));
    Wallet wallet2 = new Wallet(UUID.randomUUID(), UUID.fromString("a2fe49e3-900d-4632-b3c1-0b6b25dd555e"), UUID.fromString("70754a2f-d87d-4f69-af71-1d4bfe855e28"));
    private final static String DELETE_WALLETS_QUERY = "delete from wallets;";
    private final static String DELETE_CUSTOMERS_QUERY = "delete from customers;";
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    private final

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

        repository.create(wallet2);
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
        Wallet createWallet = service.create(wallet1.getCustomerId(), wallet1.getVoucherId());
        Assertions.assertThat(createWallet.getVoucherId()).isEqualTo(UUID.fromString("c80f7d69-5033-423c-b7d2-a11e7ee936dd"));
        Assertions.assertThat(createWallet.getCustomerId()).isEqualTo(UUID.fromString("70754a2f-d87d-4f69-af71-1d4bfe855e28"));
    }

    @Test
    @DisplayName("findByCustomerId")
    void findByCustomerId() {
        Wallet wallet = repository.create(wallet1);
        List<Voucher> voucherList = service.findByCustomerId(wallet.getCustomerId());

        Assertions.assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByVoucherId")
    void findByVoucherId() {
        Wallet wallet = repository.create(wallet1);
        List<Customer> customerList = service.findByVoucherId(wallet.getVoucherId());

        Assertions.assertThat(customerList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = service.delete(wallet2.getCustomerId(), wallet2.getVoucherId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
