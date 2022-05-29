package org.prgrms.kdt.customer.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hamcrest.Matchers;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {

    private final int VALID_SIZE = 1;

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

    @Autowired
    CustomerRepository customerRepository;

    private static final Customer newCustomer = new Customer(UUID.randomUUID(), "김형욱", LocalDateTime.now());

    @Test
    @DisplayName("사용자들 저장할 수 있다.")
    void saveTest() {
        customerRepository.save(newCustomer);
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), Matchers.is(false));
    }

    @Test
    @DisplayName("사용자를 모두 조회할 수 있다.")
    void findAllTest() {
        customerRepository.save(newCustomer);
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size(), equalTo(VALID_SIZE));
    }

    @Test
    @DisplayName("특정 사용자를 조회할 수 있다.")
    void findTest() {
        customerRepository.save(newCustomer);
        Optional<Customer> customer = customerRepository.findById(newCustomer.getId());
        assertThat(customer.isEmpty(), Matchers.is(false));
    }

    @Test
    @DisplayName("조회하려는 아이디의 사용자가 없다면 비어있는 옵셔널을 반환한다.")
    void findEmptyTest() {
        Optional<Customer> customer = customerRepository.findById(UUID.randomUUID());
        assertThat(customer.isEmpty(), Matchers.is(true));
    }

    @Test
    @DisplayName("사용자를 삭제할 수 있다.")
    void deleteAllTest() {
        customerRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), Matchers.is(true));
    }

}