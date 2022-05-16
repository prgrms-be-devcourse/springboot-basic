package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.configuration.DataSourceProperties;
import com.programmers.springbootbasic.configuration.YamlPropertiesFactory;
import com.programmers.springbootbasic.domain.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.springbootbasic"})
    static class Config {

        @Bean
        public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                    @Value("${spring.datasource.username}") String username,
                                    @Value("${spring.datasource.password}")String password) {
            return DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer1, newCustomer2;

    @BeforeAll
    void initBeforeTest() {
        customerJdbcRepository.deleteAll();

        newCustomer1 = new Customer("JiSungPark", "jisung");
        newCustomer2 = new Customer("LionelMessi", "messi");
    }

    @Test
    @Order(1)
    @DisplayName("HikariDataSource 빈을 정상적으로 주입할 수 있다.")
    void testDataSourceConnection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("서로 다른 고객 두 명을 customers 테이블에 저장할 수 있다.")
    void testInsert() {
        customerJdbcRepository.insert(newCustomer1);
        customerJdbcRepository.insert(newCustomer2);
    }

    @Test
    @Order(3)
    @DisplayName("존재하는 고객 아이디에 대해 일치하는 고객 객체를 반환하고 존재하지 않는 고객은 빈 객체를 반환한다.")
    void testFindById() {
        Optional<Customer> foundCustomer = customerJdbcRepository.findById(newCustomer1.getCustomerId());

        assertThat(foundCustomer.get().getCustomerId()).isEqualTo(newCustomer1.getCustomerId());

        assertThat(customerJdbcRepository.findById("존재하지 않는 아이디")).isEqualTo(Optional.empty());
    }

    @Test
    @Order(4)
    @DisplayName("저장된 고객 객체의 아이디로 해당 객체를 customers 테이블에서 삭제할 수 있다.")
    void testDeleteById() {
        customerJdbcRepository.deleteById(newCustomer2.getCustomerId());
    }

    @Test
    @Order(5)
    @DisplayName("현재 customers 테이블에 저장된 모든 객체를 정상적으로 불러올 수 있다.")
    void testFindAll() {
        List<Customer> allCustomers = customerJdbcRepository.findAll();

        assertThat(allCustomers.size()).isEqualTo(1);
        assertThat(allCustomers.get(0).getCustomerId().equals(newCustomer1.getCustomerId())).isTrue();
    }

}