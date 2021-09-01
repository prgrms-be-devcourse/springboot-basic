package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.CustomerNotFoundException;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.prgrms.kdt.kdtspringorder.custommer.repository.CustomerRepository;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Profile("test")
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("CustomerService 단위 테스트")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeAll
    public void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Nested
    @DisplayName("getCustomers 메소드는")
    class Describe_getCustomers {

        @Nested
        @DisplayName("만약 목록이 비어있다면")
        class Context_list_is_empty{

            @BeforeEach
            void setUp() {
                when(customerRepository.findAll()).thenReturn(new ArrayList<Customer>());
            }

            @Test
            @DisplayName("빈 Customer 목록을 반환합니다.")
            void it_return_empty_list() {
                final List<Customer> customers = customerService.getCustomers();
                assertThat(customers, notNullValue());
                assertThat(customers, hasSize(0));
            }

        }

        @Nested
        @DisplayName("만약 목록이 비어있지 않다면")
        class Context_list_is_not_empty{

            @BeforeEach
            void setUp() {
                when(customerRepository.findAll()).thenReturn(List.of(
                    new Customer(UUID.randomUUID(), "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now())
                ));
            }

            @Test
            @DisplayName("빈 Customer 목록을 반환합니다.")
            void it_return_not_empty_list() {
                final List<Customer> customers = customerService.getCustomers();
                assertThat(customers, not(hasSize(0)));
            }

        }

    }

    @Nested
    @DisplayName("getCustomer 메소드는")
    class Describe_getCustomer {

        @Nested
        @DisplayName("만약 Customer 목록에 없는 CustomerId로 조회한다면")
        class Context_invalid_customer_id{

            private UUID invalidCustomerId;

            @BeforeEach
            void setUp() {
                invalidCustomerId = UUID.randomUUID();
                when(customerRepository.findById(invalidCustomerId)).thenReturn(Optional.ofNullable(null));
            }

            @Test
            @DisplayName("CustomerNotFound Exception을 던집니다.")
            void it_return_throw_customer_not_found_exception() {

                assertThatThrownBy( () -> customerService.getCustomer(invalidCustomerId))
                    .isInstanceOf(CustomerNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 Customer 목록에 있는 CustomerId로 조회한다면")
        class Context_valid_customer_id{

            private UUID validCustomerId;

            @BeforeEach
            void setUp() {
                validCustomerId = UUID.randomUUID();
                when(customerRepository.findById(validCustomerId)).thenReturn(Optional.ofNullable(new Customer(validCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now())));
            }

            @Test
            @DisplayName("조회한 Customer를 반환합니다.")
            void it_return_found_customer() {

                final Customer foundCustomer = customerService.getCustomer(validCustomerId);
                assertThat(foundCustomer.getCustomerId(), equalTo(validCustomerId));

            }

        }

    }

    @Nested
    @DisplayName("createCustomer 메소드는")
    class Describe_createCustomer {

        @Nested
        @DisplayName("생성할 고객 정보를 인자로 전달한다면")
        class Context_call_method{

            private Customer newCustomer;

            @BeforeEach
            void setUp() {
                newCustomer = new Customer(UUID.randomUUID(), "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                when(customerRepository.insert(newCustomer)).thenReturn(newCustomer.getCustomerId());
            }

            @Test
            @DisplayName("생성한 고객의 ID를 반환합니다.")
            void it_return_created_customer_id() {
                final UUID createdCustomerId = customerService.createCustomer(newCustomer);
                assertThat(createdCustomerId, equalTo(newCustomer.getCustomerId()));
            }

        }

    }

    @Nested
    @DisplayName("updateCustomer 메소드는")
    class Describe_updateCustomer {

        @Nested
        @DisplayName("만약 고객목록에 없는 고객을 수정하는 경우")
        class Context_invalid_customer_id{

            private UUID invalidCustomerId;
            private Customer updateTargetCustomer;

            @BeforeEach
            void setUp() {
                invalidCustomerId = UUID.randomUUID();
                updateTargetCustomer = new Customer(invalidCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                when(customerRepository.update(updateTargetCustomer)).thenThrow(new CustomerNotFoundException(invalidCustomerId));
            }

            @Test
            @DisplayName("CustomerNotFound Exception을 던집니다.")
            void it_return_throw_customer_not_found_exception() {

                assertThatThrownBy( () -> customerService.updateCustomer(updateTargetCustomer))
                    .isInstanceOf(CustomerNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 고객목록에 있는 고객을 수정하는 경우")
        class Context_valid_customer_id{

            private UUID validCustomerId;
            private Customer updateTargetCustomer;

            @BeforeEach
            void setUp() {
                validCustomerId = UUID.randomUUID();
                updateTargetCustomer = new Customer(validCustomerId, "김지훈", "jihoon@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                when(customerRepository.update(updateTargetCustomer)).thenReturn(validCustomerId);
            }

            @Test
            @DisplayName("수정한 Customer의 CustomerId를 반환합니다.")
            void it_return_updated_customer_id() {

                final UUID updatedCustomerId = customerService.updateCustomer(updateTargetCustomer);
                assertThat(updatedCustomerId, equalTo(updateTargetCustomer.getCustomerId()));

            }

        }

    }

    @Nested
    @DisplayName("deleteCustomer 메소드는")
    class Describe_deleteCustomer {

        @Nested
        @DisplayName("만약 고객목록에 없는 고객을 삭제하는 경우")
        class Context_invalid_customer_id{

            private UUID invalidCustomerId;

            @BeforeEach
            void setUp() {
                invalidCustomerId = UUID.randomUUID();
                when(customerRepository.delete(invalidCustomerId)).thenThrow(new CustomerNotFoundException(invalidCustomerId));
            }

            @Test
            @DisplayName("CustomerNotFound Exception을 던집니다.")
            void it_return_throw_customer_not_found_exception() {

                assertThatThrownBy( () -> customerService.deleteCustomer(invalidCustomerId))
                    .isInstanceOf(CustomerNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 고객목록에 있는 고객을 삭제하는 경우")
        class Context_valid_customer_id{

            private UUID validCustomerId;

            @BeforeEach
            void setUp() {
                validCustomerId = UUID.randomUUID();
                when(customerRepository.delete(validCustomerId)).thenReturn(1);
            }

            @Test
            @DisplayName("삭제한 컬럼 수를 반환합니다.")
            void it_return_deleted_column_count() {

                final int deletedColumnCount = customerService.deleteCustomer(validCustomerId);
                assertThat(deletedColumnCount, equalTo(1));

            }

        }

    }


}
