package org.prgms.management.blacklist.repository;

import org.junit.jupiter.api.*;
import org.prgms.management.blacklist.vo.Blacklist;
import org.prgms.management.customer.entity.Customer;
import org.prgms.management.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlackListJdbcRepositoryTest {
    @Autowired
    BlackListJdbcRepository blackListJdbcRepository;

    @Autowired
    CustomerRepository customerRepository;

    List<Blacklist> newBlacklists = new ArrayList<>();
    List<Customer> newCustomers = new ArrayList<>();

    @BeforeAll
    void setup() {
        customerRepository.deleteAll();
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user1", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user2", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user3", LocalDateTime.now()));
        newCustomers.add(Customer.getCustomer(UUID.randomUUID(), "test-user4", LocalDateTime.now()));

        blackListJdbcRepository.deleteAll();
        newCustomers.forEach(v -> {
            var insert = customerRepository.insert(v);
            if (insert != null) {
                newBlacklists.add(new Blacklist(UUID.randomUUID(), v.getCustomerId(), LocalDateTime.now()));
            }
        });
    }

    @Test
    @Order(1)
    @DisplayName("블랙리스트를 추가 할 수 있다.")
    void insert() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.insert(v);
            assertThat(blacklist == null, is(false));
            assertThat(blacklist.blacklistId(), is(v.blacklistId()));
        });
    }

    @Test
    @Order(2)
    @DisplayName("중복된 블랙리스트를 추가 할 수 없다.")
    void insertDuplicateBlacklist() {
        newBlacklists.forEach(v -> {
            Assertions.assertThrows(DuplicateKeyException.class, () -> {
                blackListJdbcRepository.insert(v);
            });
        });
    }

    @Test
    @Order(3)
    @DisplayName("전체 블랙리스트를 조회 할 수 있다.")
    void findAll() {
        var blacklists = blackListJdbcRepository.findAll();
        assertThat(blacklists.size(), is(newBlacklists.size()));
    }

    @Test
    @Order(4)
    @DisplayName("아이디로 블랙리스트를 조회 할 수 있다.")
    void findById() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.findById(
                    v.blacklistId());
            assertThat(blacklist.isEmpty(), is(false));
        });
    }

    @Test
    @Order(5)
    @DisplayName("고객 아이디로 블랙리스트를 조회 할 수 있다.")
    void findByCustomerId() {
        newBlacklists.forEach(v -> {
            var blacklist = blackListJdbcRepository.
                    findByCustomerId(v.customerId());
            assertThat(blacklist.isEmpty(), is(false));
        });
    }

    @Test
    @Order(6)
    @DisplayName("블랙리스트를 삭제 할 수 있다.")
    void delete() {
        var blacklist = blackListJdbcRepository.delete(
                newBlacklists.get(0));
        assertThat(blacklist == null, is(false));
    }

    @Test
    @Order(7)
    @DisplayName("모든 블랙리스트를 삭제 할 수 있다.")
    void deleteAll() {
        blackListJdbcRepository.deleteAll();
        customerRepository.deleteAll();
        var blacklists = blackListJdbcRepository.findAll();
        assertThat(blacklists.isEmpty(), is(true));
    }

    @Test
    @Order(8)
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