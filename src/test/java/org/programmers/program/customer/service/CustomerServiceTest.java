package org.programmers.program.customer.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.program.customer.controller.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {
    @Configuration
    @ComponentScan(basePackages = "org.programmers.program.customer")
    static class Config{
        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucherDB")
                    .username("root")
                    .password("voucher1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerService customerService;

    @Autowired
    DataSource dataSource;

    @AfterAll
    void cleanUp(){
        customerService.clearCustomerRepository();
    }

    @Test
    @Order(1)
    @DisplayName("서비스 고객 추가 테스트")
    void insertTest(){
        var customerDto = new CustomerDto(UUID.randomUUID(), "test1@email.com", "test1");
        var customer = customerService.createCustomer(customerDto);

        assertThat(customer.getName()).isEqualTo("test1");
        assertThat(customer.getEmail()).isEqualTo("test1@email.com");
    }

    @Test
    @Order(2)
    @DisplayName("고객 수정 테슷흐")
    void updateTest(){
        var id = UUID.randomUUID();
        var customerDto = new CustomerDto(id, "test2@email.com", "test2");
        var insertedCustomer = customerService.createCustomer(customerDto);

        var updateCustomer = new CustomerDto(id, "updated@email.com", "updated").to();

        var updatedCustomer = customerService.updateUser(updateCustomer);

        assertThat(customerService.findById(id).get().getId()).isEqualTo(id);
        assertThat(customerService.findById(id).get().getEmail()).isEqualTo("updated@email.com");
    }
}
