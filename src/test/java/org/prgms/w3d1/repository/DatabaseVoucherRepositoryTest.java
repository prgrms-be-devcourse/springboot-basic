package org.prgms.w3d1.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.w3d1.model.voucher"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/my_order_mgmt")
                .username("root")
                .password("1111")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DatabaseVoucherRepository databaseVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new DatabaseVoucherRepository(jdbcTemplate);
        }

        @Bean
        CustomerJdbcRepository databaseCustomerRepository(JdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DatabaseVoucherRepository databaseVoucherRepository;

    @Autowired
    CustomerJdbcRepository databaseCustomerRepository;

    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    void setUp() {
        fixedAmountVoucher = FixedAmountVoucher.of(UUID.randomUUID(), 50L);
        percentDiscountVoucher = PercentDiscountVoucher.of(UUID.randomUUID(), 25L);
    }

    @BeforeEach
    void methodSetup() {
        databaseVoucherRepository.deleteAll();
        databaseCustomerRepository.deleteAll();
    }


    @Test
    @Order(1)
    @DisplayName("Id를 통해서 바우처 조회")
    void findById() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 DBMS에 저장")
    void save() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("모든 바우처 꺼내기")
    void findAll() {
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.save(percentDiscountVoucher);

        var testVouchers = databaseVoucherRepository.findAll();

        assertThat(testVouchers, containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }

    /*
        given : 새로운 고객을 만들고
        when : 해당 고객 id를 voucher에 할당한다.
        then : 고객 id로 바우처를 찾아 확인한다.
     */

    @Test
    @Order(4)
    @DisplayName("특정 고객 할당하기")
    void testAssignCustomer() {
        var customerId = UUID.randomUUID();
        databaseCustomerRepository.insert(new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now()));
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.assignCustomer(customerId, fixedAmountVoucher.getVoucherId());

        var voucher = databaseVoucherRepository.findByCustomerId(customerId);
        assertThat(voucher.isEmpty(), is(false));
    }

    /*
        given : 고객을 만들어 DB에 저장하고, 특정 바우처를 만들어 고객 id를 등록한뒤
        when : 해당 바우처가 포함된 지갑을 가져오면
        then : 바우처가 존재해야한다.
     */
    @Test
    @Order(5)
    @DisplayName("바우처 지갑 가져오기")
    void testFindVoucherWallet() {
        var customerId = UUID.randomUUID();
        databaseCustomerRepository.insert(new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now()));
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.assignCustomer(customerId, fixedAmountVoucher.getVoucherId());

        var voucherWallet = databaseVoucherRepository.findVoucherWallet(customerId);
        assertThat(voucherWallet.getVoucherWallet().size(), not(0));
    }

    /*
        given : 고객을 만들어 DB에 저장하고, 특정 바우처를 만들어 고객 id를 등록한뒤
        when : 해당 바우처를 삭제하면
        then : 고객 바우처 지갑에 아무것도 없어야한다.
     */

    @Test
    @Order(6)
    @DisplayName("고객의 바우처 삭제하기")
    void deleteCustomerVoucher() {
        var customerId = UUID.randomUUID();
        databaseCustomerRepository.insert(new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now()));
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.assignCustomer(customerId, fixedAmountVoucher.getVoucherId());

        databaseVoucherRepository.deleteCustomerVoucher(customerId, fixedAmountVoucher.getVoucherId());
        var voucherWallet = databaseVoucherRepository.findVoucherWallet(customerId);
        assertThat(voucherWallet.getVoucherWallet().size(), is(0));
    }
}