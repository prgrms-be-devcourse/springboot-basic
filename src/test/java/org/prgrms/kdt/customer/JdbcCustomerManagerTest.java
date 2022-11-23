package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(JdbcConfig.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerManagerTest {

    @Autowired
    private CustomerManager customerManager;

    private EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void init() {
        customerManager.deleteAll();
    }

    @DisplayName("고객은 저장될 수 있다.")
    @Test
    void saveTest() {
        // given
        String customerName = "test1";
        Customer customer = new Customer(customerName);

        // when
        Customer savedCustomer = customerManager.save(customer);

        // then
        assertThat(customerManager.findById(savedCustomer.getId()).isPresent())
                .isTrue();
    }

    @DisplayName("모든 고객을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        Customer customer1 = new Customer("test1");
        Customer customer2 = new Customer("test2");
        Customer savedCustomer1 = customerManager.save(customer1);
        Customer savedCustomer2 = customerManager.save(customer2);

        // when
        List<Customer> actualCustomers = customerManager.findAll();

        // then

        assertThat(actualCustomers)
                .usingRecursiveComparison()
                .isEqualTo(List.of(savedCustomer1, savedCustomer2));
    }

    @DisplayName("아이디로 고객의 정보를 조회할 수 있다.")
    @Test
    void findIdTest() {
        // given
        Customer customer = new Customer("test");
        Customer savedCustomer = customerManager.save(customer);

        // when
        Optional<Customer> actual = customerManager.findById(savedCustomer.getId());

        // then
        assertThat(actual.isPresent())
                .isTrue();
    }

    @DisplayName("고객의 정보를 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        Customer customer = new Customer("test1");
        Customer savedCustomer = customerManager.save(customer);
        Customer updatedCustomer = new Customer(savedCustomer.getId(), "update");

        // when
        customerManager.update(updatedCustomer);
        Customer actualCustomer = customerManager.findById(updatedCustomer.getId()).get();
        String actualName = actualCustomer.getName();

        // then
        assertThat(actualName)
                .isEqualTo("update");

    }

    @DisplayName("고객을 삭제할 수 있다.")
    @Test
    void deleteByIdTest() {
        // given
        Customer customer = new Customer("test1");
        Customer savedCustomer = customerManager.save(customer);

        // when
        customerManager.deleteById(savedCustomer.getId());

        // then
        assertThat(customerManager.findById(savedCustomer.getId()).isPresent())
                .isFalse();
    }
}