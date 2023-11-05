package com.prgms.vouchermanager.service.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.dto.CreateCustomerDto;
import com.prgms.vouchermanager.dto.UpdateCustomerDto;
import com.prgms.vouchermanager.repository.customer.CustomerRepository;
import com.prgms.vouchermanager.validation.InputValidation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    private CustomerRepository customerRepsotiory;
    @Mock
    private InputValidation inputValidation;

    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 정상 실행하고, 값을 받아올수 있다.")
    void createCusteomerSuccess() {

        //given
        CreateCustomerDto dto = new CreateCustomerDto("kim", "won05121@naver.com", 1);
        Customer result = new Customer(null, dto.getName(), dto.getEmail(), true);

        when(customerRepsotiory.save(any(Customer.class))).thenReturn(result);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())).thenReturn(true);

        //when
        Customer customer = customerService.create(dto);

        //then
        Assertions.assertThat(customer).isEqualTo(result);

    }

    @Test
    @DisplayName("service의 create()를 실행시 입력값 검증 실패로 RuntimeException 예외가 발생한다.")
    void createCusteomerFail() {
        //given
        CreateCustomerDto dto = new CreateCustomerDto("kim", "won05121@naver.com", 12345);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList()))
                .thenReturn(false);

        //when
        //then
        Assertions.assertThatThrownBy(() -> customerService.create(dto)).
                isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("service의 update()를 통해 repository의 update()가 정상적으로 실행된다.")
    void updateCustomerSuccess() {
        //given
        UpdateCustomerDto dto = new UpdateCustomerDto("kim", "won05121@naver.com", 1);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())).thenReturn(true);

        //when
        customerService.update(1L, dto);

        //then
        verify(customerRepsotiory, atLeast(1)).update(any(Customer.class));
    }

    @Test
    @DisplayName("service의 update()를 통해 입력값 검증 실패시 RuntimeException예외가 발생한다.")
    void updateCustomerFail() {
        //given
        UpdateCustomerDto dto = new UpdateCustomerDto("kim", "won05121@naver.com", 1);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())).thenReturn(false);

        //when
        //then
        Assertions.assertThatThrownBy(() -> customerService.update(1L, dto)).isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("service의 findById()로 존재하는 ID가 들어오면 ,repository의 findById()는 성공적으로 값을 찾아온다. .")
    void findByIdCustomerSuccess() {
        //given
        Customer customer = new Customer(1L, "kk", "kk@naver.com", true);
        when(customerRepsotiory.findById(customer.getId()))
                .thenReturn(Optional.ofNullable(customer));

        //when
        //then
        Assertions.assertThat(customer).isEqualTo(customerService.findById(customer.getId()));

    }

    @Test
    @DisplayName("service의 findById()의 인자로 존재하지 않는 ID가 들어오면 EmptyResultDataAccessException 예외가 발생한다 .")
    void findByIdCustomerFail() {
        //given
        Long notExistId = 1L;
        //when
        when(customerRepsotiory.findById(any())).thenReturn(Optional.ofNullable(null));

        //then
        Assertions.assertThatThrownBy(() -> customerService.findById(notExistId)).isInstanceOf(EmptyResultDataAccessException.class);

    }

    @Test
    @DisplayName("service의 findAll()을 통해 repository 의 findAll()실행시켜 성공적으로 값을 얻는다.")
    void findAll() {
        //given
        List<Customer> customers = List.of(new Customer(1L, "kk", "kk@naver.com", true));
        when(customerRepsotiory.findAll()).thenReturn(customers);

        //when
        List<Customer> customerList = customerService.findAll();

        //then
        Assertions.assertThat(customers).isEqualTo(customerList);

    }

    @Test
    @DisplayName("service의 deleteById()의 인자와 상관없이, repository의 deleteById()는 반드시 한번은 실행된다.")
    void deleteByIdSuccess() {
        //given
        //when
        customerService.deleteById(null);

        //then
        verify(customerRepsotiory, atLeastOnce()).deleteById(any());
    }

    @Test
    @DisplayName("service의 deleteAll()을 통해 repository 의 deleteAll() 을 반드시 한번은 실행한다.")
    void deleteAll() {
        //given
        //when
        customerService.deleteAll();

        //then
        verify(customerRepsotiory, atLeastOnce()).deleteAll();
    }

    @Test
    @DisplayName("service의 getBlackList()를 통해 repository의 getBlackList()을 실행시켜 성공적으로 값을 찾아온다.")
    void getBlackList() {
        //given
        List<Customer> blackList = List.of(new Customer(1L, "kk", "kk@naver.com", true));

        //when
        when(customerRepsotiory.findBlackList()).thenReturn(blackList);

        //then
        Assertions.assertThat(blackList).isEqualTo(customerService.findBlackList());

    }
}
