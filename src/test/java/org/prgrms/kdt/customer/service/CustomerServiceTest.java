package org.prgrms.kdt.customer.service;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdt.config.TestConfig;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class CustomerServiceTest {

    private static final UUID VALID_CUSTOMER_ID = UUID.randomUUID();
    private static final String VALID_NAME = "김형욱";
    private static final LocalDateTime VALID_CREATED_AT = LocalDateTime.now();

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setUp() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-voucher_mgmt", classPathScripts("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자를 만들 수 있다.")
    void makeCustomerTest() {
        // GIVEN
        Customer customer = new Customer(VALID_CUSTOMER_ID, VALID_NAME, VALID_CREATED_AT);

        // WHEN
        Customer newCustomer = customerService.makeCustomer(customer);

        // THEN
        assertThat(newCustomer, samePropertyValuesAs(customer));
        assertThat(customer.getId(), is(VALID_CUSTOMER_ID));
        assertThat(customer.getName(), is(VALID_NAME));
    }

    @Test
    @DisplayName("아이디를 통해 사용자를 조회할 수 있다.")
    void getCustomerTest() {
        // GIVEN
        Customer customer = new Customer(VALID_CUSTOMER_ID, VALID_NAME, VALID_CREATED_AT);
        Customer newCustomer = customerService.makeCustomer(customer);

        // WHEN
        Customer findCustomer = customerService.getCustomer(VALID_CUSTOMER_ID);

        // THEN
        assertThat(findCustomer, samePropertyValuesAs(findCustomer));
    }

    @Test
    @DisplayName("아이디에 해당하는 사용자가 없다면 예외를 발생시킨다.")
    void getCustomerNotFoundTest() {
        // GIVEN
        // WHEN
        // THEN
        assertThrows(NoSuchElementException.class, () -> {
            customerService.getCustomer(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("사용자를 전체조회 할 수 있다.")
    void getCustomersTest() {
        // GIVEN
        Customer customer = new Customer(VALID_CUSTOMER_ID, VALID_NAME, VALID_CREATED_AT);
        Customer newCustomer = customerService.makeCustomer(customer);

        // WHEN
        List<Customer> customers = customerService.getCustomers();

        // THEN
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("사용자를 전체 삭제할 수 있다.")
    void deleteCustomersTest() {
        // GIVEN
        customerService.deleteCustomers();

        // WHEN
        List<Customer> customers = customerRepository.findAll();

        // THEN
        assertThat(customers.isEmpty(), is(true));
    }

}
