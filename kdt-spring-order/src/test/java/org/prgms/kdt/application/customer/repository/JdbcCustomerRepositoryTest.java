package org.prgms.kdt.application.customer.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.application.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class JdbcCustomerRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-db", classPathScript("sql/schema.sql"))
            .start();
    }

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void beforeEach() {
        customer = new Customer(
            UUID.randomUUID(),
            "sample1",
            "sample1@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.insert(customer);
    }

    @AfterEach
    void dataCleanup() {
        customerRepository.deleteAll();
    }

    @Test
    void getBlacklist() {
    }

    @Test
    @DisplayName("Customer insert 성공")
    void insert() {
        Customer customer = new Customer(
            UUID.randomUUID(),
            "example",
            "example@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Customer insertCustomer = customerRepository.insert(customer);
        assertThat(customer).isEqualTo(insertCustomer);
    }

    @Test
    @DisplayName("Customer update 성공")
    void update() {
        Customer updateCustomer = new Customer(
            customer.getCustomerId(),
            "update",
            "update@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.update(customer);
    }

    @Test
    @DisplayName("전체 고객을 조회")
    void findAll() {
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("customerId로 고객 찾기")
    void findById() {
        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.get()).isEqualTo(customer);
        Optional<Customer> findUnknownCustomer = customerRepository.findById(UUID.randomUUID());
        assertThat(findUnknownCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customerName 으로 고객 찾기")
    void findByName() {
        Optional<Customer> findCustomer = customerRepository.findByName(customer.getName());
        assertThat(findCustomer.get()).isEqualTo(customer);
        Optional<Customer> findUnknownCustomer = customerRepository.findByName("unknownName");
        assertThat(findUnknownCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customerId를 이용해서 customer 삭제")
    void delete() {
        int delete = customerRepository.delete(customer.getCustomerId());
        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customers 테이블의 모든 tuple 삭제")
    void deleteAll() {
        customerRepository.deleteAll();
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty()).isTrue();
    }
}