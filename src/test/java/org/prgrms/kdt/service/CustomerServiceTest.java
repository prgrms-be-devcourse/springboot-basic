package org.prgrms.kdt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.dto.CustomerDto;
import org.prgrms.kdt.exception.TypedException;
import org.prgrms.kdt.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @Nested
  @DisplayName("register는")
  class Describe_register {

    @Nested
    @DisplayName("중복된 이메일이 들어오면")
    class Context_with_duplicated_email {

      @Test
      @DisplayName("에러를 반환한다.")
      void it_throws_exception() {
        var existEmail = "exist@email.com";
        given(customerRepository.existsByEmail(existEmail)).willReturn(true);

        assertThatThrownBy(() -> customerService.register(new CustomerDto("new", existEmail)))
            .isInstanceOf(TypedException.class)
            .hasMessage("Error: Email already exists");
      }
    }

    @Nested
    @DisplayName("중복되지 않은 이메일이 들어오면")
    class Context_with_non_duplicated_email {

      @Test
      @DisplayName("고객을 등록한다.")
      void it_register_customer() {
        var newEmail = "new-email@gmail.com";
        given(customerRepository.existsByEmail(newEmail)).willReturn(false);
        given(customerRepository.save(any(Customer.class))).willReturn(
            new Customer("abc", newEmail));

        var sut = customerService.register(new CustomerDto("abc", newEmail));

        assertThat(sut).isNotNull();
        assertThat(sut.getEmail()).isEqualTo(newEmail);
        verify(customerRepository, times(1)).save(any(Customer.class));
      }
    }
  }
}