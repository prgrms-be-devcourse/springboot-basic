package com.prgrms.vouchermanager.repository.wallet;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.wallet.Wallet;
import com.prgrms.vouchermanager.repository.customer.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.customer.CustomerRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
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

@SpringJUnitConfig
class WalletRepositoryTest {

    private WalletRepository repository;
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
        Wallet wallet = new Wallet(voucher2.getId(), customer1.getId());
        Wallet createWallet = repository.create(wallet);

        Assertions.assertThat(createWallet.getVoucherId()).isSameAs(wallet.getVoucherId());
        Assertions.assertThat(createWallet.getCustomerId()).isSameAs(wallet.getCustomerId());
    }

    @Test
    @DisplayName("findByCustomerId")
    void findByCustomerId() {
        Wallet wallet2 = new Wallet(voucher2.getId(), customer1.getId());
        repository.create(wallet2);
        List<Wallet> walletList = repository.findByCustomerId(customer1.getId());

        Assertions.assertThat(walletList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByVoucherId")
    void findByVoucherId() {
        Wallet wallet2 = new Wallet(voucher2.getId(), customer1.getId());
        repository.create(wallet2);
        List<Wallet> walletList1 = repository.findByVoucherId(voucher1.getId());
        List<Wallet> walletList2 = repository.findByVoucherId(voucher2.getId());

        Assertions.assertThat(walletList1.size()).isEqualTo(1);
        Assertions.assertThat(walletList2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = repository.delete(wallet1.getCustomerId(), wallet1.getVoucherId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
