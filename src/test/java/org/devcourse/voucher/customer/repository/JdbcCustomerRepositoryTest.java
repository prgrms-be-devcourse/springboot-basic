package org.devcourse.voucher.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.repository.CustomerRepository;
import org.devcourse.voucher.core.exception.DataUpdateFailException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    private CustomerRepository customerRepository;

    private Pageable pageable;

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void init() {
        pageable = Pageable.ofSize(5);
    }

    private final Customer newCustomer = new Customer(UUID.randomUUID(),
            "yongcheol",
            new Email("yongcheol@devcourse.com"));

    private List<Customer> customerStubsCreate() {
        ArrayList<Customer> customers = new ArrayList<>(List.of(
                new Customer(UUID.randomUUID(), "test1", new Email("test1@test.com")),
                new Customer(UUID.randomUUID(), "test2", new Email("test2@test.com")),
                new Customer(UUID.randomUUID(), "test3", new Email("test3@test.com")),
                new Customer(UUID.randomUUID(), "test4", new Email("test4@test.com"))
        ));
        customers.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getCustomerId().toString().compareTo(c2.getCustomerId().toString());
            }
        });
        return customers;
    }

    @Test
    @DisplayName("데이터베이스에 값이 잘 들어가는지 확인하는 테스트")
    void customerInsertTest() {
        customerRepository.insert(newCustomer);

        Customer customer = customerRepository.findAll(pageable).get(0);

        assertThat(customer).usingRecursiveComparison().isEqualTo(newCustomer);
    }

    @Test
    @DisplayName("중복된 데이터를 넣으려고 할 경우 예외가 발생하는지 테스트")
    void duplicateCustomerInsertTest() {
        customerRepository.insert(newCustomer);

        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> customerRepository.insert(newCustomer));
    }

    @Test
    @DisplayName("DB에 들어있는 데이터를 업데이트 했을 때 잘 업데이트가 되는지 테스트")
    void customerUpdateTest() {
        customerRepository.insert(newCustomer);
        newCustomer.setName("yongc");

        customerRepository.update(newCustomer);
        Customer customer = customerRepository.findAll(pageable).get(0);

        assertThat(customer.getName()).isEqualTo("yongc");
    }

    @Test
    @DisplayName("DB에 존재하지 않는 데이터를 업데이트 시도시 예외가 발생하는지 테스트")
    void notExistsCustomerUpdateTest() {

        assertThatExceptionOfType(DataUpdateFailException.class)
                .isThrownBy(() -> customerRepository.update(newCustomer));
    }

    @Test
    @DisplayName("DB에 존재하는 데이터가 잘 조회되는지 테스트")
    void customerFindAllTest() {
        List<Customer> stubs = customerStubsCreate();

        for (Customer customer : stubs) {
            customerRepository.insert(customer);
        }
        List<Customer> customers = customerRepository.findAll(pageable);

        assertThat(customers).usingRecursiveComparison().isEqualTo(stubs);
    }

    @Test
    @DisplayName("DB에 존재하는 데이터가 전부 잘 삭제되는지 테스트")
    void customerDeleteAllTest() {
        List<Customer> stubs = customerStubsCreate();

        for (Customer customer : stubs) {
            customerRepository.insert(customer);
        }
        customerRepository.deleteAll();

        assertThat(customerRepository.findAll(pageable)).isEmpty();
    }
}