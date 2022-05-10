package com.waterfogsw.voucher.customer.service;

import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerDefaultServiceTest {

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerDefaultService service;

    @Nested
    @DisplayName("addCustomer 메서드는")
    class Describe_addCustomer {

        @Nested
        @DisplayName("customer 가 null 이면")
        class Context_with_call {

            @Test
            @DisplayName("IllegalArgumentException 에러를 던진다")
            void It_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> service.addCustomer(null));
            }
        }

        @Nested
        @DisplayName("customer 가 null 이 아니면")
        class Context_with_not_null {

            @Test
            @DisplayName("insert 메서드를 호출한다")
            void It_call_insert() {
                final var customer = new Customer("hello");

                service.addCustomer(customer);
                verify(repository).insert(any());
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("id 가 0 이하 이면")
        class Context_with_call {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0L})
            @DisplayName("IllegalArgumentException 에러를 던진다")
            void It_IllegalArgumentException(long id) {
                assertThrows(IllegalArgumentException.class, () -> service.findById(id));
            }
        }

        @Nested
        @DisplayName("id 가 0 이하가 아니면")
        class Context_with_not_null {

            @Test
            @DisplayName("findById 메서드를 호출한다")
            void It_call_insert() {
                service.findById(1);
                verify(repository).findById(anyLong());
            }
        }
    }
}