package com.programmers.kwonjoosung.springbootbasicjoosung.repository;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.JdbcCustomerRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.JdbcVoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherRepository;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet.JdbcWalletRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcWalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "com.programmers.kwonjoosung.springbootbasicjoosung.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("wallet-schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    JdbcWalletRepository jdbcWalletRepository;
    CustomerRepository customerRepository;
    VoucherRepository voucherRepository;
    @Autowired JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void setUp() {
        this.jdbcWalletRepository = new JdbcWalletRepository(jdbcTemplate);
        this.customerRepository = new JdbcCustomerRepository(jdbcTemplate);
        this.voucherRepository = new JdbcVoucherRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("바우처Id를 통해서 바우처를 소유한 고객을 조회할 수 있다")
    void findByVoucherIdTest(){
        //given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer.getCustomerId(), customer.getName());

        // when
        jdbcWalletRepository.insert(customer.getCustomerId(), voucher.getVoucherId());
        //then
        assertEquals( customer.getCustomerId(), jdbcWalletRepository.findByVoucherId(voucher.getVoucherId()));
    }
    @Test
    @DisplayName("고객이 가진 모든 바우처를 조회할 수 있다.")
    void findByCustomerIdTest(){
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher1);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        voucherRepository.insert(voucher2);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer.getCustomerId(), customer.getName());
        // when
        jdbcWalletRepository.insert(customer.getCustomerId(), voucher1.getVoucherId());
        jdbcWalletRepository.insert(customer.getCustomerId(), voucher2.getVoucherId());
        List<UUID> vouchersByCustomerId = jdbcWalletRepository.findByCustomerId(customer.getCustomerId());
        //then
        assertEquals(voucher1.getVoucherId(),vouchersByCustomerId.get(0));
        assertEquals(voucher2.getVoucherId(),vouchersByCustomerId.get(1));
    }

    @Test
    @DisplayName("고객이 가진 바우처를 삭제할 수 있다.")
    void deleteVoucherTest(){
        //given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, UUID.randomUUID(), 1000);
        voucherRepository.insert(voucher1);
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, UUID.randomUUID(), 10);
        voucherRepository.insert(voucher2);
        Customer customer = new Customer(UUID.randomUUID(), "joosung");
        customerRepository.insert(customer.getCustomerId(), customer.getName());

        jdbcWalletRepository.insert(customer.getCustomerId(), voucher1.getVoucherId());
        jdbcWalletRepository.insert(customer.getCustomerId(), voucher2.getVoucherId());
        // when
        jdbcWalletRepository.delete(voucher1.getVoucherId());
        List<UUID> vouchersByCustomerId = jdbcWalletRepository.findByCustomerId(customer.getCustomerId());
        //then
        assertEquals(voucher2.getVoucherId(),vouchersByCustomerId.get(0));
        assertThrows(IndexOutOfBoundsException.class,() -> vouchersByCustomerId.get(1));
    }
    
    

}
