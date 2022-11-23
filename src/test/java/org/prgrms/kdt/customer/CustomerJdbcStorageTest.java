package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class CustomerJdbcStorageTest {

    private static final int NAME_LENGTH = 4;

    @Autowired
    private CustomerJdbcStorage customerJdbcStorage;

    private Customer customer;
    private String customerId;
    private EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setJdbc() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("customer.sql"))
                .start();
    }

    @BeforeEach
    void setup() {
        customerId = UUID.randomUUID().toString();
        String name = RandomString.make(NAME_LENGTH);
        customer = new Customer(customerId, name, name + "@gmail.com");

        customerJdbcStorage.insert(customer);
    }

    @AfterEach
    void clear(){
        embeddedMysql.reloadSchema("voucher_mgmt", classPathScript("customer.sql"));
    }

    @AfterAll
    void exitTest() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("고객을 새로 추가할 수 있다.")
    void testInsertNewCustomer() {
        // given
        String newCustomerId = UUID.randomUUID().toString();
        Customer newCustomer = new Customer(newCustomerId, "new-user", "new-user@gmail.com");

        // when
        customerJdbcStorage.insert(newCustomer);
        Customer findCustomer = customerJdbcStorage.findById(newCustomerId).get();

        assertThat(newCustomer).usingRecursiveComparison()
                .isEqualTo(findCustomer);
    }

    @Test
    @DisplayName("고객의 아이디를 통해 특정 고객을 조회할 수 있다.")
    void testFindCustomerById() {
        // given
        String customerId = customer.getCustomerId();

        // when
        Customer findCustomer = customerJdbcStorage.findById(customerId).get();

        // then
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(findCustomer);

    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    void testFindAllCustomer() {
        // when
        List<Customer> allCustomer = customerJdbcStorage.findAll();

        // then
        assertFalse(allCustomer.isEmpty());
    }

    @Test
    @DisplayName("고객 아이디를 활용하여 특정 고객을 저장소에서 삭제할 수 있다.")
    void testDeleteCustomerById() {
        // given
        String id = UUID.randomUUID().toString();
        Customer deleteCustomer = new Customer(id, "delete-user", "delete-user@gmail.com");

        customerJdbcStorage.insert(deleteCustomer);

        // when
        customerJdbcStorage.deleteCustomerById(deleteCustomer.getCustomerId());

        // then
        assertEquals(Optional.empty(),
                customerJdbcStorage.findById(
                        deleteCustomer.getCustomerId()));
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다.")
    void testChangeCustomerName() {
        // given
        String name = "new-name";
        customer.changeName(name);

        // when
        customerJdbcStorage.update(customer);
        Customer getCustomer = customerJdbcStorage.findById(customerId).get();

        // then
        assertThat(customer).usingRecursiveComparison()
                .isEqualTo(getCustomer);
    }
}
