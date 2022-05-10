package org.prgrms.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucher.models.Customer;
import org.prgrms.voucher.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Nested
    @DisplayName("Service create 메서드는")
    class DescribeCreate {

        @Nested
        @DisplayName("create 기능을 테스트 할 때 null인 customer객체를 받으면")
        class ContextReceiveNullCustomer{

            Customer customer = null;

            @Test
            @DisplayName("잘못된 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> customerService.create(customer))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("create 기능을 테스트할 때 customer를 인자로 받으면")
        class ContextReceiveCustomer {

            Customer customer = new Customer(1L, "jack", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("저장하기 위해 repository의 save 메서드를 호출한다.")
            void itCallRepositorySave() {

                customerService.create(customer);

                verify(customerRepository).save(customer);
            }

            @Test
            @DisplayName("생성된 customer를 반환한다.")
            void itCreateCustomerAndReturn() {

                when(customerRepository.save(any(Customer.class))).thenReturn(customer);

                Customer checkCustomer = customerService.create(customer);

                Assertions.assertThat(customer.getCustomerName()).isEqualTo(checkCustomer.getCustomerName());
            }
        }
    }

    @Nested
    @DisplayName("getCustomers 메서드는")
    class DescribeList {

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("Repository의 findAll 메서드를 호출한다.")
            void itCallRepositoryFindAll() {

                customerService.getCustomers();

                verify(customerRepository).findAll();
            }

            @Test
            @DisplayName("고객을 담은 리스트를 반환한다.")
            void itReturnCustomerList() {

                Customer firstCustomer = new Customer(1L, "jack", LocalDateTime.now(), LocalDateTime.now());
                Customer secondCustomer = new Customer(2L, "mick", LocalDateTime.now(), LocalDateTime.now());

                List<Customer> expectedCustomers = List.of(firstCustomer, secondCustomer);

                when(customerRepository.findAll()).thenReturn(expectedCustomers);

                List<Customer> actualCustomers = customerService.getCustomers();

                Assertions.assertThat(expectedCustomers).isEqualTo(actualCustomers);
            }
        }
    }

    @Nested
    @DisplayName("Service getCustomer 메서드는")
    class DescribeGetCustomer {

        @Nested
        @DisplayName("인자로 받은 ID가 존재하지 않으면")
        class ContextReceiveWrongId {

            Long wrongId = -1L;
            Optional<Customer> emptyCustomer = Optional.empty();

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itThrowIllegalArgumentException() {

                when(customerRepository.findById(wrongId)).thenReturn(emptyCustomer);

                Assertions.assertThatThrownBy(() -> customerService.getCustomerById(wrongId))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("인자로 받은 ID가 존재하면")
        class ContextReceiveValidId {

            Long validId = 1L;

            Customer customer = new Customer(1L, "jack", LocalDateTime.now(), LocalDateTime.now());

            Optional<Customer> expectedCustomer = Optional.of(customer);

            @Test
            @DisplayName("해당 바우처를 반환한다..")
            void itReturnCustomer() {

                when(customerRepository.findById(validId)).thenReturn(expectedCustomer);

                Customer actualCustomer = customerService.getCustomerById(validId);

                Assertions.assertThat(expectedCustomer.get()).isEqualTo(actualCustomer);
            }
        }
    }

    @Nested
    @DisplayName("Service deleteVoucherById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("인자로 받은 ID가 존재하지 않으면")
        class ContextReceiveWrongId {

            Long wrongId = -1L;

            Optional<Customer> expectedCustomer = Optional.empty();

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itThrowIllegalArgumentException() {

                when(customerRepository.findById(wrongId)).thenReturn(expectedCustomer);

                Assertions.assertThatThrownBy(() -> customerService.deleteCustomerById(wrongId))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("인자로 받은 ID가 존재하면")
        class ContextReceiveValidId {

            Long validId = 1L;

            Customer customer = new Customer(1L, "jack", LocalDateTime.now(), LocalDateTime.now());

            Optional<Customer> expectedCustomer = Optional.of(customer);

            @Test
            @DisplayName("해당 바우처를 삭제한다..")
            void itDeleteCustomer() {

                when(customerRepository.findById(validId)).thenReturn(expectedCustomer);

                customerService.deleteCustomerById(validId);

                verify(customerRepository).deleteById(validId);
            }
        }
    }
}