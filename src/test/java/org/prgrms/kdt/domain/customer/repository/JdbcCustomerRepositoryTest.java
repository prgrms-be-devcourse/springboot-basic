package org.prgrms.kdt.domain.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher")
                    .username("park")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void save() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        //when
        UUID savedId = customerRepository.save(customer);
        //then
        assertThat(savedId).isEqualTo(customerId);
    }

    @Test
    public void findAll() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0)).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    public void findById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        //then
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    public void findByVoucherId() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "park", "d@naver.com", now, now);
        customerRepository.save(customer);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);
        //when
        Optional<Customer> findCustomer = customerRepository.findByVoucherId(voucherId);
        //then
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    public void updateById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        int updateCnt = customerRepository.updateById(new Customer(customerId, "kim", "a@naver.com", CustomerType.NORMAL, LocalDateTime.now(), LocalDateTime.now()));
        //then
        assertThat(updateCnt).isEqualTo(1);
    }

    @Test
    public void deleteById() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        customerRepository.deleteById(customerId);
        List<Customer> customers = customerRepository.findAll();
        //then
        assertThat(customers).isEmpty();
    }

    @Test
    public void deleteAll() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK_LIST, now, now);
        customerRepository.save(customer);
        //when
        customerRepository.deleteAll();
        //then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }
}