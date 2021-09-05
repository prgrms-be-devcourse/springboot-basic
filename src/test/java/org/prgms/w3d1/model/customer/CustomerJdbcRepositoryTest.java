package org.prgms.w3d1.model.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.w3d1.model.customer"})
    static class config {

        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/my_order_mgmt")
                .username("root")
                .password("1111")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public CustomerJdbcRepository customerRepository(JdbcTemplate jdbcTemplate){
            return new CustomerJdbcRepository(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user",
            "test-user@gmail.com", LocalDateTime.now());
        customerJdbcRepository.deleteAll();
    }

    /*
        테스트 : Hikari와 연결이 잘 되었는가
     */
    @Test
    @Order(0)
    public void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    /*
        테스트 : customer를 데이터베이스에 넣는다
        Given : custeomer가 주어지면
        when : insert 메서드가 수행되고, 해당 customer를 다시 불러올 때
        then : customer와 불러온 customer를 비교한다
     */
    @Test
    void insert() {
        customerJdbcRepository.insert(newCustomer);

        var testCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(testCustomer.isEmpty(), is(false));
        assertThat(testCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    /*
        테스트 : update
        Given : custeomer의 이름을 바꾸고
        when : update 메서드가 수행되어 해당 customer를 다시 불러올 때
        then : customer와 불러온 customer를 비교한다
     */
    @Test
    void update() {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        var testCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(testCustomer.isEmpty(), is(false));
        assertThat(testCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    /*
        테스트 : 전체 고객 조회
        Given
        When : findAll 메서드가 실행될 때
        then : list가 비어있는지 확인한다
     */
    @Test
    void findAll() {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    /*
        테스트 : id로 고객 찾기
        Given : 유저를 insert 한 뒤
        then : id를 이용해서 불러온 새로운 유저와 비교한다
     */
    @Test
    void findById() {
        var testCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(testCustomer.isEmpty(), is(false));

        var unknownCustomer = customerJdbcRepository.findById(UUID.randomUUID());
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    void findByName() {
        var testCustomer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(testCustomer.isEmpty(), is(false));

        var unknownCustomer = customerJdbcRepository.findByName("unKnown Name");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    void findByEmail() {
        var testCustomer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(testCustomer.isEmpty(), is(false));

        var unknownCustomer = customerJdbcRepository.findByEmail("unKnown@gmail.com");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    /*
        테스트 : 특정 생성일 사이의 고객들을 조회한다
        Given : 2개의 LocalDateTime 객체를 주고
        when : findByCratedDateRange 메서드로 조회한다.
        then : 불러온 리스트가 비었는지 확인한다.
     */
    @Test
    void findCreatedByDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 0, 0);

        var createdList = customerJdbcRepository.findByCreatedDateRange(startDate, endDate);

        assertThat(createdList.isEmpty(), is(false));
    }
}