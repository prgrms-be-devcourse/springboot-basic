package org.prgrms.kdt.domain.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
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
    JdbcCustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void save() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK, now, now);
        //when
        UUID savedId = repository.save(customer);
        //then
        assertThat(savedId).isEqualTo(customerId);
    }

    @Test
    public void findAll() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK, now, now);
        repository.save(customer);
        //when
        List<Customer> customers = repository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    public void update() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"park" , "dbslzld15@naver.com", CustomerType.BLACK, now, now);
        repository.save(customer);
        //when
        int updateCnt = repository.updateById(new Customer(customerId, "kim", "a@naver.com", CustomerType.NORMAL, LocalDateTime.now(), LocalDateTime.now()));
        //then
        assertThat(updateCnt).isEqualTo(1);
    }
}