package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.dto.CustomerRequest;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private BlackListRepository fileCustomerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer() {
        return new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
    }

    private List<Customer> customers() {
        return List.of(new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", LocalDateTime.now()));
    }

    private CustomerRequest customerRequest() {
        return new CustomerRequest("hwan", "hwan@gmail.com");
    }

    private Email email() {
        return new Email("hwan@gmail.com");
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("중복되지 않는 이메일을 가진 고객이라면")
        class Context_with_non_duplicate_email_customer {
            final CustomerRequest customerRequest = customerRequest();
            final Customer customer = new Customer(UUID.randomUUID(), customerRequest.getName(),
                customerRequest.getEmail(),
                LocalDateTime.now());

            @BeforeEach
            void prepare() {
                given(customerRepository.save(any(Customer.class))).willReturn(customer);
                given(customerRepository.findByEmail(anyString())).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("고객을 저장하고 저장된 고객을 리턴한다.")
            void it_saves_customer_and_returns_saved_customer() {
                Customer saveCustomer = customerService.save(customerRequest);

                assertThat(saveCustomer).usingRecursiveComparison().isEqualTo(customer);
                then(customerRepository).should(times(1)).save(any(Customer.class));
                then(customerRepository).should(times(1)).findByEmail(anyString());
            }
        }

        @Nested
        @DisplayName("중복되는 이메일을 가진 고객이라면")
        class Context_with_duplicate_email_customer {
            final CustomerRequest customerRequest = customerRequest();
            final Customer savedCustomer = new Customer(UUID.randomUUID(), customerRequest.getName(),
                customerRequest.getEmail(),
                LocalDateTime.now());

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(savedCustomer));
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> customerService.save(customerRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.");
                then(customerRepository).should(times(1)).findByEmail(anyString());
                then(customerRepository).should(times(0)).save(any(Customer.class));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("저장된 고객들이 있다면")
        class Context_with_saved_customers {
            final List<Customer> customers = customers();

            @BeforeEach
            void prepare() {
                given(customerRepository.findAll()).willReturn(customers);
            }

            @Test
            @DisplayName("모든 고객을 리턴한다.")
            void it_returns_all_customer() {
                List<Customer> findCustomers = customerService.findCustomers();

                //then
                assertThat(findCustomers).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(findCustomers);
                then(customerRepository).should(times(1)).findAll();
            }
        }
    }

    @Nested
    @DisplayName("findByEmail 메서드는")
    class Describe_findByEmail {

        @Nested
        @DisplayName("저장된 고객의 이메일이라면")
        class Context_with_saved_customer_email {
            final Customer customer = customer();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
            }

            @Test
            @DisplayName("고객을 찾고 찾은 고객을 리턴한다.")
            void it_find_customer_and_returns_customer() {
                Customer findCustomer = customerService.findByEmail(new Email(customer.getEmail()));

                //then
                assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
                then(customerRepository).should(times(1)).findByEmail(any(String.class));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 고객의 이메일이라면")
        class Context_with_not_saved_customer_email {
            final Email notSavedEmail = email();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> customerService.findByEmail(notSavedEmail))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
                then(customerRepository).should(times(1)).findByEmail(any(String.class));
            }
        }
    }

    @Nested
    @DisplayName("updateCustomer 메서드는")
    class Describe_updateCustomer {

        @Nested
        @DisplayName("해당 이메일로 저장된 고객이 있고 수정할 이메일이 중복되지 않는다면")
        class Context_with_non_duplicate_update_customer {
            final Email email = email();
            final Customer customer = customer();
            final CustomerRequest customerRequest = new CustomerRequest("spancer", "spancer@gmail.com");

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
                customer.changeInformation(customerRequest.getName(), customerRequest.getEmail(), LocalDateTime.now());
                given(customerRepository.update(any(Customer.class))).willReturn(customer);
            }

            @Test
            @DisplayName("전달받은 정보대로 고객을 수정한다.")
            void it_update_customer_with_request() {
                Customer updateCustomer = customerService.update(email, customerRequest);

                assertThat(updateCustomer).usingRecursiveComparison()
                    .isEqualTo(customer);
                then(customerRepository).should(times(2)).findByEmail(any(String.class));
                then(customerRepository).should(times(1)).update(any(Customer.class));
            }
        }

        @Nested
        @DisplayName("해당 이메일로 저장된 고객이 있고 수정할 이메일이 중복된다면")
        class Context_with_duplicate_update_customer {
            Email email = email();
            CustomerRequest customerRequest = new CustomerRequest("aramand", "aramand@gmail.com");
            Customer customer = customer();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(email.getEmail())).willReturn(Optional.of(customer));
                customer.changeInformation(customerRequest.getName(), customerRequest.getEmail(), LocalDateTime.now());
                Customer duplicateCustomer = new Customer(UUID.randomUUID(), "spancer", "aramand@gmail.com",
                    LocalDateTime.now());
                given(customerRepository.findByEmail(customer.getEmail())).willReturn(Optional.of(duplicateCustomer));
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> customerService.update(email, customerRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.");
                then(customerRepository).should(times(2)).findByEmail(any(String.class));
                then(customerRepository).should(times(0)).update(any(Customer.class));
            }
        }

        @Nested
        @DisplayName("해당 이메일로 저장된 고객이 없다면")
        class Context_with_not_saved_email_customer {
            final Email email = email();
            final CustomerRequest customerRequest = customerRequest();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(email.getEmail())).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> customerService.update(email, customerRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
                then(customerRepository).should(times(1)).findByEmail(any(String.class));
                then(customerRepository).should(times(0)).update(any(Customer.class));
            }
        }

    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("요청받은 이메일로 저장된 고객이 있다면")
        class Context_with_saved_email_customer {
            final Customer customer = customer();
            final Email email = new Email(customer.getEmail());

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
            }

            @Test
            @DisplayName("고객을 삭제한다.")
            void it_delete_customer() {
                customerService.delete(email);

                then(customerRepository).should(times(1)).findByEmail(customer.getEmail());
                then(customerRepository).should(times(1)).deleteByEmail(customer.getEmail());
            }
        }

        @Nested
        @DisplayName("요청받은 이메일로 저장된 고객이 없다면")
        class Context_with_not_saved_email_customer {
            final Email email = email();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> customerService.delete(email))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");

                then(customerRepository).should(times(1)).findByEmail(any(String.class));
                then(customerRepository).should(times(0)).deleteByEmail(any(String.class));
            }
        }
    }

    @Nested
    @DisplayName("findBlackList 메서드는")
    class Describe_findBlackList {

        @Nested
        @DisplayName("고객들 중 블랙 고객이 있다면")
        class Context_with_black_customer {
            final List<Customer> mockBlackCustomers = customers();

            @BeforeEach
            void prepare() {
                given(fileCustomerRepository.findBlackCustomers()).willReturn(mockBlackCustomers);
            }

            @Test
            @DisplayName("모든 블랙 고객들을 리턴한다.")
            void it_returns_all_black_customers() {
                List<Customer> blackList = customerService.findBlackList();

                assertThat(blackList).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(mockBlackCustomers);
                then(fileCustomerRepository).should(times(1)).findBlackCustomers();
            }
        }
    }

}
