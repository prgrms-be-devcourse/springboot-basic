package org.prgms.kdt.application.customer.service;

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
import org.prgms.kdt.application.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class CustomerServiceTest {

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
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
            UUID.randomUUID(),
            "sample2",
            "sample2@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.insert(customer);
    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 가입이 가능하다.")
    void join() {
        Customer newCustomer = customerService.join(new Customer(
            UUID.randomUUID(),
            "example",
            "example@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        Optional<Customer> findCustomer = customerService.getCustomerById(newCustomer.getCustomerId());
        assertThat(newCustomer).isEqualTo(findCustomer.get());
    }

    @Test
    @DisplayName("중복된 이름을 가진 고객으로 가입시에 에러 발생한다.")
    void duplicatedNameJoin() {
        assertThatThrownBy(() -> customerService.join(new Customer(
            UUID.randomUUID(),
            "sample2",
            "sample2@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        log.info("{}", allCustomers.get(0).getName());
        assertThat(allCustomers.size()).isNotEqualTo(0);
    }

    @Test
    @DisplayName("customer 이름, 이메일 수정 가능하다.")
    void updateCustomer() {
        Customer updateCustomer = new Customer(this.customer.getCustomerId(), "updateName",
            "updateName@gmail.com", this.customer.getCreatedAt(), this.customer.getUpdatedAt());
        customerService.updateCustomer(updateCustomer);
        Optional<Customer> findCustomer = customerService.getCustomerById(customer.getCustomerId());
        assertThat(updateCustomer).isEqualTo(findCustomer.get());
    }

    @Test
    @DisplayName("customerId를 이용해서 customer 삭제")
    void deleteCustomerById() {
        customerService.deleteCustomerById(customer.getCustomerId());
        Optional<Customer> findCustomer = customerService.getCustomerById(customer.getCustomerId());
        assertThat(findCustomer.isEmpty()).isTrue();
    }
}