package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.springbootbasic.repository"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url("jdbc:mysql://localhost/voucher_mgmt?useUnicode=true&serverTimezone=UTC")
                    .username("test")
                    .password("test")
                    .build();

            return dataSource;
        }
    /*
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
     */
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    CustomerDTO newCustomer1, newCustomer2;

    @BeforeAll
    void initBeforeTest() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testDataSourceConnection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    void testInsert() {
        newCustomer1 = new CustomerDTO("JiSungPark", "박지성");
        customerJdbcRepository.insert(newCustomer1);

        newCustomer2 = new CustomerDTO("LionelMessi", "메시");
        customerJdbcRepository.insert(newCustomer2);
    }

    @Test
    @Order(3)
    void testFindById() {
        Optional<CustomerDTO> foundCustomer = customerJdbcRepository.findById(newCustomer1.getCustomerId());
        assertThat(foundCustomer.get().getCustomerId()).isEqualTo(newCustomer1.getCustomerId());

//        assertThatThrownBy(() -> customerJdbcRepository.findById("존재하지 않는 아이디"))
//                .isInstanceOf(DataAccessException.class);
    }

    @Test
    @Order(4)
    void testDeleteById() {
        customerJdbcRepository.deleteById("LionelMessi");
    }

    @Test
    @Order(5)
    void testFindAll() {
        customerJdbcRepository.findAll().forEach(System.out::println);
    }

}