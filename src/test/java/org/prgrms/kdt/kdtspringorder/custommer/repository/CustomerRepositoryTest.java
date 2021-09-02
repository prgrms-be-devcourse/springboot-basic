package org.prgrms.kdt.kdtspringorder.custommer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.kdtspringorder.common.exception.CustomerNotFoundException;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerRepository 단위 테스트")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){

        MysqldConfig config = MysqldConfig.aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
//            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order-mgmt", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("만약 목록이 비어있다면")
        class Context_list_is_empty{

            @Test
            @DisplayName("빈 Customer 목록을 반환합니다.")
            void it_return_empty_list() {
                final List<Customer> customers = customerRepository.findAll();
                assertThat(customers, notNullValue());
                assertThat(customers, hasSize(0));
            }

        }

        @Nested
        @DisplayName("만약 목록이 비어있지 않다면")
        class Context_list_is_not_empty{

            private Customer newCustomer;

            @BeforeEach
            void setUp() {
                newCustomer = new Customer(UUID.randomUUID(), "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                customerRepository.insert(newCustomer);
            }

            @Test
            @DisplayName("빈 Customer 목록을 반환합니다.")
            void it_return_not_empty_list() {
                final List<Customer> customers = customerRepository.findAll();
                assertThat(customers, not(hasSize(0)));
            }

        }

    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById{

        @Nested
        @DisplayName("CustomerId로 조회한다면")
        class Context_customer_id{

            private UUID customerId;

            @BeforeEach
            void setUp() {
                customerId = UUID.randomUUID();
            }

            @Test
            @DisplayName("Optional<Customer>를 반환합니다.")
            void it_return_customer_optional() {
                final Optional<Customer> foundCustomerOptional = customerRepository.findById(customerId);
                assertThat(foundCustomerOptional, not(nullValue()));
                assertThat(foundCustomerOptional, instanceOf(Optional.class));
            }

        }

    }

    @Nested
    @DisplayName("insert 메소드는")
    class Describe_insert {

        @Nested
        @DisplayName("생성할 고객 정보를 인자로 전달한다면")
        class Context_call_method{

            private Customer newCustomer;

            @BeforeEach
            void setUp() {
                newCustomer = new Customer(UUID.randomUUID(), "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
            }

            @Test
            @DisplayName("생성한 고객의 ID를 반환합니다.")
            void it_return_created_customer_id() {
                final UUID createdCustomerId = customerRepository.insert(newCustomer);
                assertThat(createdCustomerId, equalTo(newCustomer.getCustomerId()));
            }

        }

    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("만약 고객목록에 없는 고객을 수정하는 경우")
        class Context_invalid_customer_id {

            private UUID invalidCustomerId;
            private Customer updateTargetCustomer;

            @BeforeEach
            void setUp() {
                invalidCustomerId = UUID.randomUUID();
                updateTargetCustomer = new Customer(invalidCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
            }

            @Test
            @DisplayName("CustomerNotFound Exception을 던집니다.")
            void it_return_throw_customer_not_found_exception() {

                assertThatThrownBy(() -> customerRepository.update(updateTargetCustomer))
                    .isInstanceOf(CustomerNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 고객목록에 있는 고객을 수정하는 경우")
        class Context_valid_customer_id {

            private UUID validCustomerId;
            private Customer updateTargetCustomer;
            private Customer changeContentCustomer;

            @BeforeEach
            void setUp() {
                validCustomerId = UUID.randomUUID();
                updateTargetCustomer = new Customer(validCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                customerRepository.insert(updateTargetCustomer);

                changeContentCustomer = new Customer(validCustomerId, "김지훈2", "update@gmail.com", LocalDateTime.now(), LocalDateTime.now());

                validCustomerId = customerRepository.update(updateTargetCustomer);

            }

            @Test
            @DisplayName("내용을 수정한 Customer의 CustomerId를 반환합니다.")
            void it_return_updated_customer_id() {

                final UUID updatedCustomerId = customerRepository.update(changeContentCustomer);

                final Customer updatedCustomer = customerRepository.findById(updatedCustomerId).get();

                assertThat(updatedCustomerId, equalTo(updateTargetCustomer.getCustomerId()));
                assertThat(updateTargetCustomer, not(samePropertyValuesAs(updatedCustomer)));

            }

        }

    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 고객목록에 없는 고객을 삭제하는 경우")
        class Context_invalid_customer_id{

            private UUID invalidCustomerId;

            @BeforeEach
            void setUp() {
                invalidCustomerId = UUID.randomUUID();
            }

            @Test
            @DisplayName("CustomerNotFound Exception을 던집니다.")
            void it_return_throw_customer_not_found_exception() {

                assertThatThrownBy( () -> customerRepository.delete(invalidCustomerId))
                    .isInstanceOf(CustomerNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 고객목록에 있는 고객을 삭제하는 경우")
        class Context_valid_customer_id{

            private UUID validCustomerId;
            private Customer newCustomer;

            @BeforeEach
            void setUp() {
                validCustomerId = UUID.randomUUID();
                newCustomer = new Customer(validCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                customerRepository.insert(newCustomer);
            }

            @Test
            @DisplayName("삭제한 컬럼 수를 반환합니다.")
            void it_return_deleted_column_count() {

                final int deletedColumnCount = customerRepository.delete(validCustomerId);
                assertThat(deletedColumnCount, equalTo(1));
                final Optional<Customer> foundCustomerOptional = customerRepository.findById(validCustomerId);
                assertThat(foundCustomerOptional.isPresent(), is(false));

            }

        }

    }

}


