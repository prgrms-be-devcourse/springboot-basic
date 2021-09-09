package com.prgrms.devkdtorder.customer.repository;

import com.prgrms.devkdtorder.customer.domain.BlackCustomers;
import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.customer.domain.CustomerType;
import com.prgrms.devkdtorder.customer.domain.TestCustomerBuilder;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class JdbcCustomerRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CustomerRepository customerRepository;
    private static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();

    }

    @AfterAll
    static void cleanUp() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void init() {
        customerRepository = new JdbcCustomerRepository(namedParameterJdbcTemplate);
    }


    @Test
    @DisplayName("고객을 추가 할 수 있다")
    void testInsert() {
        log.info("called testInsert..");
        //given
        Customer customer = new Customer(UUID.randomUUID(), "maeng", "maeng@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        //when
        customerRepository.insert(customer);
        //then
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("블랙된 고객만을 가져올 수 있다")
    void testFindAllBlackCustomers() {
        log.info("called testFindAllBlackCustomers..");
        //given
        givenCustomer(UUID.randomUUID(), "maeng", "maeng@gmail.com", CustomerType.WHITE);
        givenCustomer(UUID.randomUUID(), "maeng1", "maeng1@gmail.com",CustomerType.BLACK);
        givenCustomer(UUID.randomUUID(), "maeng2", "maeng2@gmail.com", CustomerType.BLACK);
        givenCustomer(UUID.randomUUID(), "maeng3", "maeng3@gmail.com", CustomerType.BLACK);
        //when
        BlackCustomers allBlackCustomers = customerRepository.findAllBlackCustomers();
        //then
        assertThat(allBlackCustomers, notNullValue());
        assertThat(allBlackCustomers.size(), is(3));

    }

    @Test
    @DisplayName("고객 추가시 중복된 이메일이 존재하면 에러가 발생해야 한다")
    void testDuplicationNameInsert() {
        //given
        givenCustomer(UUID.randomUUID(), "maeng", "maeng@gmail.com", CustomerType.WHITE);
        //when then
        assertThatThrownBy(() -> givenCustomer(UUID.randomUUID(), "maeng1", "maeng@gmail.com",CustomerType.WHITE))
                .isInstanceOf(DuplicateKeyException.class);
    }


    private void givenCustomer(UUID customerId, String name, String email, CustomerType customerType) {
        Customer customer = TestCustomerBuilder.start()
                .customerId(customerId).name(name).email(email).customerType(customerType).build();
        customerRepository.insert(customer);
    }
}