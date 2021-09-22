package org.prgrms.devcourse.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.devcourse.customer.Customer;
import org.prgrms.devcourse.customer.JdbcCustomerRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcVoucherWalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.devcourse.voucher"})
    static class JdbcVoucherWalletRepositoryTestConfig {

        @Bean
        public DataSource dataSource() {
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
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

        @Bean
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcCustomerRepository(jdbcTemplate);
        }

        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }

        @Bean
        public JdbcVoucherWalletRepository jdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherWalletRepository(jdbcTemplate);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcVoucherWalletRepository jdbcVoucherWalletRepository;

    @Autowired
    DataSource dataSource;

    Voucher newVoucher;
    Customer newCustomer;
    VoucherUseInfo newVoucherUseInfo;

    @BeforeAll
    void setup() {
        newVoucher = Voucher.of(UUID.randomUUID(), 90L, VoucherType.PERCENT_DISCOUNT_VOUCHER);
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        newVoucherUseInfo = new VoucherUseInfo(UUID.randomUUID(), newCustomer.getCustomerId(), newVoucher.getVoucherId());
    }

    @BeforeEach
    void initTest() {
        jdbcCustomerRepository.save(newCustomer);
        jdbcVoucherRepository.insert(newVoucher);
        jdbcVoucherWalletRepository.insert(newVoucherUseInfo);
    }

    @AfterEach
    void clean() {
        jdbcVoucherWalletRepository.deleteAll();
        jdbcVoucherRepository.deleteAll();
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적으로 바우처를 할당할 수 있다.")
    void voucherIssueTest() {
        var testVoucherUseInfo = new VoucherUseInfo(UUID.randomUUID(), newCustomer.getCustomerId(), newVoucher.getVoucherId());

        var issuedVoucher = jdbcVoucherWalletRepository.insert(testVoucherUseInfo);

        assertThat(jdbcVoucherWalletRepository.findOne(testVoucherUseInfo.getVoucherUseInfoId()).get(), samePropertyValuesAs(issuedVoucher));
    }

    @Test
    @DisplayName("할당된 바우처를 조회할 수 있다.")
    void findVoucherUseInfoTest() {
        var testVoucherUseInfo = jdbcVoucherWalletRepository.insert(new VoucherUseInfo(UUID.randomUUID(), newCustomer.getCustomerId(), newVoucher.getVoucherId()));

        var voucherUseInfoList1 = jdbcVoucherWalletRepository.findByVoucherId(testVoucherUseInfo.getVoucherId());
        var voucherUseInfoList2 = jdbcVoucherWalletRepository.findByCustomerId(testVoucherUseInfo.getCustomerId());

        assertThat(voucherUseInfoList1.isEmpty(), is(false));
        assertThat(voucherUseInfoList1, hasSize(voucherUseInfoList2.size()));
    }

    @Test
    @DisplayName("할당된 바우처를 회수할 수 있다.")
    void recallVoucherUseInfoTest() {
        var testVoucherUseInfoId = jdbcVoucherWalletRepository.delete(newVoucherUseInfo.getVoucherUseInfoId());

        assertThat(jdbcVoucherWalletRepository.findOne(testVoucherUseInfoId).isEmpty(), is(true));
    }

    @Test
    @DisplayName("할당된 바우처의 만료일을 변경할 수 있다.")
    void updateVoucherUseInfoTest() {
        newVoucherUseInfo.setExpiredAt(newVoucherUseInfo.getExpiredAt().plusMonths(1));
        var retrievedVoucherUseInfo1 = jdbcVoucherWalletRepository.findOne(newVoucherUseInfo.getVoucherUseInfoId()).get();

        jdbcVoucherWalletRepository.update(newVoucherUseInfo);
        var retrievedVoucherUseInfo2 = jdbcVoucherWalletRepository.findOne(newVoucherUseInfo.getVoucherUseInfoId()).get();

        assertThat(retrievedVoucherUseInfo1.getExpiredAt().plusMonths(1), is(retrievedVoucherUseInfo2.getExpiredAt()));
    }
}

