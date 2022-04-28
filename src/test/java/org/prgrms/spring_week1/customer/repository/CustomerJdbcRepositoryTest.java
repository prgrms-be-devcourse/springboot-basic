package org.prgrms.spring_week1.customer.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.spring_week1.customer.model.Customer;
import org.prgrms.spring_week1.customer.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup(){
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-week1", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private static final Customer customer = new Customer(UUID.randomUUID(), "customer1", "mapogu",
        Gender.FEMALE, "010-234-1234");

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Order(2)
    @DisplayName("고객을 id로 검색할 수 있다")
    void findByIdTest() {
        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get(), samePropertyValuesAs(customer));

    }

    @Test
    @Order(1)
    @DisplayName("고객을 생성할 수 있다")
    void insertTest() {
        customerRepository.insert(customer);
        List<Customer> customers = customerRepository.getAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("고객을 수정할 수 있다.")
    void updateTest() {
        customer.setName("change-name");
        Customer updateCustomer = customerRepository.update(customer);

        assertThat(updateCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @Order(4)
    @DisplayName("고객을 전체삭제 할 수 있다.")
    void deleteAllTest() {
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.getAll();
        assertThat(customers.isEmpty(), is(true));
    }
}