package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.CustomerJDBCRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.repository.VoucherJDBCRepository;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletJDBCRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(WalletJDBCRepositoryTest.class);
    @Autowired
    WalletJDBCRepository walletJDBCRepository;
    @Autowired
    VoucherJDBCRepository voucherJDBCRepository;
    @Autowired
    CustomerJDBCRepository customerJDBCRepository;
    @Autowired
    DataSource dataSource;

    @AfterAll
    void cleanTableAfterTest() {
        walletJDBCRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("의존성 주입 테스트.")
    void injectionTest() {
        assertThat(walletJDBCRepository).isNotNull();
        assertThat(voucherJDBCRepository).isNotNull();
        assertThat(customerJDBCRepository).isNotNull();
    }

    @Test
    @DisplayName("고객 id와 바우처 id 정보를 함께 저장할 수 있다.")
    void saveSucceed() {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(333), VoucherType.FIXED);
        voucherJDBCRepository.save(newVoucher);
        Customer newCustomer = new Customer(UUID.randomUUID(), "바우처 주인");
        customerJDBCRepository.save(newCustomer);

        Ownership newOwnership = new Ownership(newVoucher.getVoucherId(), newCustomer.getCustomerId());
        walletJDBCRepository.save(newOwnership);
    }

    @Test
    @DisplayName("이미 할당된 바우처라면, 고객에게 바우처를 할당할 수 없다.")
    void saveAllocatedVoucherFail() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(333), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "바우처 주인12");
        customerJDBCRepository.save(customer);

        Ownership newOwnership = new Ownership(voucher.getVoucherId(), customer.getCustomerId());
        walletJDBCRepository.save(newOwnership);

        Customer newCustomer = new Customer(UUID.randomUUID(), "바우처 주인212");
        customerJDBCRepository.save(newCustomer);

        Ownership newOwnership2 = new Ownership(voucher.getVoucherId(), newCustomer.getCustomerId());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            walletJDBCRepository.save(newOwnership2);
        });
        System.err.println(exception.getMessage());
    }

    @Test
    @DisplayName("고객 id에 해당하는 고객이 없다면, 고객에게 바우처를 할당할 수 없다.")
    void saveNonExistCustomerFail() {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(333), VoucherType.FIXED);
        voucherJDBCRepository.save(newVoucher);
        UUID nonExistCustomer = UUID.randomUUID();

        Ownership newOwnership = new Ownership(newVoucher.getVoucherId(), nonExistCustomer);
        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(newOwnership));
    }

    @Test
    @DisplayName("바우처 id에 해당하는 바우처가 없다면, 바우처를 고객에게 할당할 수 없다.")
    void saveNonExistVoucherFail() {
        UUID nonExistVoucher = UUID.randomUUID();
        Customer newCustomer = new Customer(UUID.randomUUID(), "바우처 주인3");
        customerJDBCRepository.save(newCustomer);

        Ownership newOwnership = new Ownership(nonExistVoucher, newCustomer.getCustomerId());
        assertThrows(RuntimeException.class, () -> walletJDBCRepository.save(newOwnership));
    }

    @Test
    @DisplayName("고객 id로 고객이 가진 바우처 id를 가져올 수 있다.")
    void findAllVoucherByCustomerId() {
        Customer customer = new Customer(UUID.randomUUID(), "바우처 주인B");
        customerJDBCRepository.save(customer);
        for (int i = 1; i < 6; i++) {
            Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(i), VoucherType.FIXED);
            voucherJDBCRepository.save(newVoucher);
            Ownership newOwnership = new Ownership(newVoucher.getVoucherId(), customer.getCustomerId());
            walletJDBCRepository.save(newOwnership);
        }

        assertThat(walletJDBCRepository.findAllVoucherByCustomerId(customer.getCustomerId()).isEmpty()).isFalse();
    }

    @Test
    @DisplayName("함께 저장된 고객 id와 바우처 id 정보를 삭제할 수 있다.")
    void delete() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(333), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "바우처 주인1122");
        customerJDBCRepository.save(customer);

        Ownership newOwnership = new Ownership(voucher.getVoucherId(), customer.getCustomerId());
        walletJDBCRepository.save(newOwnership);

        walletJDBCRepository.delete(newOwnership);
    }

    @Test
    @DisplayName("바우처 id로 바우처를 가진 고객 id를 가져올 수 있다.")
    void findCustomerByVoucherId() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(555), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "r1sdgd2r");
        customerJDBCRepository.save(customer);
        Ownership newOwnership = new Ownership(voucher.getVoucherId(), customer.getCustomerId());
        walletJDBCRepository.save(newOwnership);

        Optional<UUID> customerId = walletJDBCRepository.findCustomerByVoucherId(voucher.getVoucherId());
        assertThat(customerId.isPresent()).isTrue();
        assertThat(customerId.get()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("바우처를 지우면 바우처 소유 정보가 사라진다.")
    void autoDeleteAfterVoucherDelete() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(555), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "바우처DFAFsdSD");
        customerJDBCRepository.save(customer);
        Ownership newOwnership = new Ownership(voucher.getVoucherId(), customer.getCustomerId());
        walletJDBCRepository.save(newOwnership);

        voucherJDBCRepository.delete(voucher.getVoucherId());

        assertThat(walletJDBCRepository.findCustomerByVoucherId(voucher.getVoucherId())).isEmpty();
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.vouchermanagement"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/test")
                    .username("root")
                    .password("980726")
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

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }
}