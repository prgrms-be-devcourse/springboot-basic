package org.prgrms.vouchermanager.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class CustomerJDBCRepositoryTest {

    @Test
    void insert_고객을_삽입할_수_있다() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test01@email.com");

        //when
        customerJdbcRepository.insert(customer);

        //then
        assertThat(customerJdbcRepository.findById(customer.getCustomerId())).isEqualTo(customer);
    }

    @Autowired
    CustomerRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.vouchermanager"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @Test
    void update_고객을_업데이트_할_수_있다() {
    }

    @Test
    void findAll_모든_고객을_리스트로_조회할_수_있다() {

    }

    @Test
    void findById_customerId로_고객을_조회할_수_있다() {
    }

    @Test
    void findByName_customer_name으로_고객을_조회할_수_있다() {
    }

    @Test
    void findByEmail_customer_email로_고객을_조회할_수_있다() {
    }

    @Test
    void delete_customer_id로_고객을_삭제할_수_있다() {
    }

    @Test
    void deleteAll_고객을_전부_삭제_할_수_있다() {
    }

}