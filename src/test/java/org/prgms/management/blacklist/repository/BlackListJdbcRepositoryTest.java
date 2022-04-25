package org.prgms.management.blacklist.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.customer.entity.Customer;
import org.prgms.management.customer.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local-db")
class BlackListJdbcRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgms.management.blacklist",
            "org.prgms.management.customer"})
    @EnableTransactionManagement
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    BlackListJdbcRepository blackListJdbcRepository;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    List<Blacklist> newBlacklists = new ArrayList<>();
    List<Customer> newCustomers = new ArrayList<>();

    @BeforeAll
    void setup() {
        customerJdbcRepository.deleteAll();
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user1", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user2", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user3", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user4", LocalDateTime.now()));

        blackListJdbcRepository.deleteAll();
        newCustomers.forEach(v -> {
            var insert = customerJdbcRepository.insert(v);
            if (insert.isPresent()) {
                newBlacklists.add(new Blacklist(UUID.randomUUID(), v.getCustomerId(), LocalDateTime.now()));
            }
        });
    }

    @Test
    @Order(1)
    void testConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("블랙리스트를 추가 할 수 있다.")
    void insert() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.insert(v);
            assertThat(blacklist.isEmpty(), is(false));
            assertThat(blacklist.get().blacklistId(), is(v.blacklistId()));
        });
    }

    @Test
    @Order(3)
    @DisplayName("중복된 블랙리스트를 추가 할 수 없다.")
    void insertDuplicateBlacklist() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.insert(v);
            assertThat(blacklist.isEmpty(), is(true));
        });
    }

    @Test
    @Order(4)
    @DisplayName("전체 블랙리스트를 조회 할 수 있다.")
    void findAll() {
        var blacklists = blackListJdbcRepository.findAll();
        assertThat(blacklists.size(), is(newBlacklists.size()));
    }

    @Test
    @Order(5)
    @DisplayName("아이디로 블랙리스트를 조회 할 수 있다.")
    void findById() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.findById(
                    v.blacklistId());
            assertThat(blacklist.isEmpty(), is(false));
        });
    }

    @Test
    @Order(6)
    @DisplayName("고객 아이디로 블랙리스트를 조회 할 수 있다.")
    void findByCustomerId() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.
                    findByCustomerId(v.customerId());
            assertThat(blacklist.isEmpty(), is(false));
        });
    }

    @Test
    @Order(7)
    @DisplayName("블랙리스트를 삭제 할 수 있다.")
    void delete() {
        var blacklist = blackListJdbcRepository.delete(
                newBlacklists.get(0));
        assertThat(blacklist.isEmpty(), is(false));
    }

    @Test
    @Order(8)
    @DisplayName("모든 블랙리스트를 삭제 할 수 있다.")
    void deleteAll() {
        blackListJdbcRepository.deleteAll();
        customerJdbcRepository.deleteAll();
        var blacklists = blackListJdbcRepository.findAll();
        assertThat(blacklists.isEmpty(), is(true));
    }

    @Test
    @Order(9)
    @DisplayName("삭제한 블랙리스트는 검색 할 수 없다.")
    void findDeleted() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.findByCustomerId(v.customerId());
            assertThat(blacklist.isEmpty(), is(true));
            blacklist = blackListJdbcRepository.findById(v.blacklistId());
            assertThat(blacklist.isEmpty(), is(true));
        });
    }
}