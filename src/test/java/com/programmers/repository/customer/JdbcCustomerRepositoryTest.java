package com.programmers.repository.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.domain.customer.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_test_db")
                    .username("admin")
                    .password("admin1234")
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
    }

    @Autowired
    private DataSource dataSource;

    private JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeEach
    void setUp() {
        jdbcCustomerRepository = new JdbcCustomerRepository(dataSource);
    }

    @AfterEach
    void after() {
        jdbcCustomerRepository.deleteAll();
    }

    @DisplayName("회원을 저장한다")
    @Test
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testCustomer");

        //when
        Customer result = jdbcCustomerRepository.save(customer);

        //then
        assertThat(result.getCustomerId(), is(customer.getCustomerId()));
    }

    @DisplayName("저장된 회원들을 모두 조회한다")
    @Test
    void findAll() {
        //given
        Customer customer1 = new Customer("cus1");
        Customer customer2 = new Customer("cus2");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        //when
        List<Customer> result = jdbcCustomerRepository.findAll();

        //then
        assertThat(result.size(), is(2));
    }

    @DisplayName("회원을 id로 조회한다")
    @Test
    void findById() {
        //given
        Customer customer = new Customer("customerName");
        jdbcCustomerRepository.save(customer);

        //when
        Optional<Customer> result = jdbcCustomerRepository.findById(customer.getCustomerId());

        //then
        assertThat(result.get().getCustomerId(), is(customer.getCustomerId()));
    }

    @DisplayName("회원을 id로 조회했을 때 존재하지 않으면 예외처리한다")
    @Test
    void findByIdException() {
        //given
        Customer customer = new Customer( "customerName");

        //when
        //then
        assertThatThrownBy(() -> jdbcCustomerRepository.findById(customer.getCustomerId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원을 수정한다")
    @Test
    void update() {
        //given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "before");
        jdbcCustomerRepository.save(customer);

        //when
        Customer result = jdbcCustomerRepository.update(new Customer(id, "after"));

        //then
        assertThat(result.getCustomerId(), is(id));
        assertThat(result.getCustomerName(), is("after"));
    }

    @DisplayName("id로 회원을 삭제한다")
    @Test
    void deleteById() {
        //given
        Customer customer = new Customer( "customerName");
        jdbcCustomerRepository.save(customer);

        //when
        jdbcCustomerRepository.deleteById(customer.getCustomerId());
        List<Customer> result = jdbcCustomerRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("저장된 모든 회원들을 삭제한다")
    @Test
    void deleteAll() {
        //given
        Customer customer1 = new Customer("cus1");
        Customer customer2 = new Customer("cus2");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        //when
        jdbcCustomerRepository.deleteAll();
        List<Customer> result = jdbcCustomerRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }
}