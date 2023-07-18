package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;

import com.example.demo.domain.customer.Customer;
import com.example.demo.dto.CustomerDto;
import com.example.demo.repository.customer.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    @DisplayName("고객 id로 고객 정보를 읽어올 수 있다.")
    void read() {
        Customer customerExpect = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        given(customerRepository.findById(customerExpect.getId()))
                .willReturn(Optional.of(customerExpect));

        // when
        CustomerDto customerDtoActual = customerService.read(customerExpect.getId());

        // then
        then(customerRepository).should(times(1)).findById(customerExpect.getId());
        assertThat(customerExpect).usingRecursiveComparison().isEqualTo(customerDtoActual);
    }

    @Test
    @DisplayName("고객 전체의 리스트를 읽어올 수 있다.")
    void readList() {
        // given
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("hanjo")
                .age(42)
                .build();

        List<Customer> customerListExpect = List.of(
                customer1,
                customer2
        );

        given(customerRepository.findAll())
                .willReturn(customerListExpect);

        // when
        List<CustomerDto> customerDtoListActual = customerService.readList();

        // then
        then(customerRepository).should(times(1)).findAll();
        assertThat(customerDtoListActual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(customerListExpect);
    }

    @Test
    @DisplayName("고객 이름 업데이트 요청을 Repository에 전달할 수 있다.")
    void update() {
        // given
        UUID customerId = UUID.randomUUID();
        String newName = "hoho";

        // when
        customerService.updateName(customerId, newName);

        // then
        then(customerRepository).should(times(1)).updateName(customerId, newName);
    }

    @Test
    @DisplayName("고객 삭제 요청을 Repository에 전달할 수 있다.")
    void delete() {
        UUID customerId = UUID.randomUUID();

        // when
        customerService.deleteById(customerId);

        // then
        then(customerRepository).should(times(1)).deleteById(customerId);//mock객체의 메서드를 호출했다는 것을 검증하는데 의미를 두어도 괜찮은가?
    }
}