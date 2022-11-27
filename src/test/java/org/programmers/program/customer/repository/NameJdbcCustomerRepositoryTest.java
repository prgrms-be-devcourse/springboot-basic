package org.programmers.program.customer.repository;

import org.junit.jupiter.api.*;
import org.programmers.program.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import javax.sql.DataSource;

import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class NameJdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = "org.programmers.program.customer")
    static class Config{

//        @Bean
//        public DataSource dataSource(){
//            return DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost/voucherDB")
//                    .username("root")
//                    .password("voucher1234!")
//                    .type(HikariDataSource.class)
//                    .build();
//        }


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
    NameJdbcCustomerRepository repository;

    @Autowired
    DataSource dataSource;

    @Test
    @Order(1)
    @DisplayName("히카리 DB 소스인지 확인")
    void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("데이터 삽입 테스트")
    void insertTest(){
        var id = UUID.randomUUID();
        repository.insert(new Customer(id, "test1@email.com", "test1"));
        var retrievedCustomer = repository.findById(id);

        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get().getId()).isEqualTo(id);
    }

    @Test
    @Order(3)
    @DisplayName("id, email, 이름으로 찾기 테스트")
    void findByTest(){
        var id2 = UUID.randomUUID();
        var email2 = "test2@email.com";
        var name2 = "test2";

        repository.insert(new Customer(id2, email2, name2));
        var byEmail = repository.findByEmail(email2);
        var byName = repository.findByName(name2);

        assertThat(byEmail).isNotEmpty();
        assertThat(byName).isNotEmpty();

        assertThat(byEmail.get().getEmail()).isEqualTo(email2);
        assertThat(byName.get().getName()).isEqualTo(name2);
    }

    @Test
    @Order(4)
    @DisplayName("수정 테스트")
    void updateTest(){
        var id = UUID.randomUUID();
        repository.insert(new Customer(id, "testtest@email.com", "testtest"));
        repository.update(new Customer(id, "changed@email.com", "changed"));

        assertThat(repository.findById(id).get().getName()).isEqualTo("changed");
    }

    @Test
    @Order(5)
    @DisplayName("find all, count 테스트")
    void findAllCountTest(){
        var allCustomers = repository.findAll();
        var customersCnt = repository.count();

        assertThat(allCustomers).hasSize(customersCnt);
    }

    @Test
    @Order(6)
    @DisplayName("전부다 지우기 테스트")
    void deleteAllTest(){
        repository.deleteAll();
        var allCustomers = repository.findAll();
        var cnt = repository.count();

        assertThat(allCustomers).isEmpty();
        assertThat(cnt).isZero();
    }
}