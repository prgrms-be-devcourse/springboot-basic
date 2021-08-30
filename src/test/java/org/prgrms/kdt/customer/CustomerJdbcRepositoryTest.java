package org.prgrms.kdt.customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:14 오후
 */
class CustomerJdbcRepositoryTest extends BaseRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer;

    @BeforeAll
    void beforeAll() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@email.com", LocalDateTime.now());
    }

    @Test
    @Order(1)
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    void testInsert() {
        customerJdbcRepository.insert(newCustomer);

        var unknown = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(unknown.isEmpty(), is(false));
        assertThat(unknown.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    void testFindAll() throws InterruptedException {
        var customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));

//        Thread.sleep(5000);
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    void testFindByName() {
        var customer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    void testFindByEmail() {
        var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));

        var unknown = customerJdbcRepository.findByEmail("unknown-user@email.com");
        assertThat(unknown.isEmpty(), is(true));
    }


    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        newCustomer.changeName("updated-user");
        customerJdbcRepository.update(newCustomer);

        var all = customerJdbcRepository.findAll();

        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

}