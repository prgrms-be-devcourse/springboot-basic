package com.prgrms.voucher_manage.domain.customer.repository;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.voucher_manage.domain.customer"}
    )
    static class JdbcConfig {
        @Value("${db.pw}")
        private String password;
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3308/order_mgmt")
                    .username("root")
                    .password(password)
                    .type(HikariDataSource.class)
                    .build();
        }

        @BeforeEach
        void beforeEach(){
            ReflectionTestUtils.setField(JdbcCustomerRepositoryTest.class,password, "root1234!");
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private JdbcCustomerRepository repository;

    @AfterEach
    public void clear() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("회원을 저장할 수 있다.")
    public void jdbcCustomerRepository_save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "와", "B");
        //when
        Customer savedCustomer = repository.save(customer);
        //then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("와");
        assertThat(savedCustomer.getType()).isEqualTo("B");
    }

    @Test
    @DisplayName("회원을 이름으로 조회할 수 있다.")
    public void jdbcCustomerRepository_findByName() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "와", "B");
        repository.save(customer);
        //when
        Customer foundCustomer = repository.findByName("와").orElse(null);

        //then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getName()).isEqualTo("와");
    }
//
//    @Test
//    @DisplayName("회원 정보를 업데이트할 수 있다.")
//    public void jdbcCustomerRepository_update(){
//        //given
//        Customer customer = saveCustomer();
//        Customer updatedCustomer = new Customer(customer.getCustomerId(), "와와", "N");
//
//        //when
//        repository.update(updatedCustomer);
//        //then
//        assertThat(updatedCustomer.getName()).isEqualTo("와와");
//        assertThat(updatedCustomer.getType()).isEqualTo("N");
//
//    }

    private Customer saveCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "와", "B");
        repository.save(customer);
        return customer;
    }

    @Test
    @DisplayName("회원 목록을 가져올 수 있다.")
    public void jdbcCustomerRepository_findAll(){
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "야", "B");
        Customer customer2 = new Customer(UUID.randomUUID(), "호", "B");
        repository.save(customer1);
        repository.save(customer2);
        //when
        List<Customer> customers = repository.findAll();
        //then
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("블랙 리스트를 가져올 수 있다.")
    public void jdbcCustomerRepository_findBlackList(){
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "야", "N");
        Customer customer2 = new Customer(UUID.randomUUID(), "호", "B");
        repository.save(customer1);
        repository.save(customer2);
        //when
        List<Customer> customers = repository.findBlackList();
        //then
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0).getName()).isEqualTo("호");
    }

//    public Customer save(){
//        Customer customer = new Customer(UUID.randomUUID(), "와", "B");
//        return repository.save(customer);
//    }
}
