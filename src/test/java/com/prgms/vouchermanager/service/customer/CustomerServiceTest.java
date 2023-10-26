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

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepsotiory;

    @Mock
    private InputValidation inputValidation;

    @InjectMocks
    CustomerService customerService;


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
        verify(customerRepsotiory,atLeastOnce()).save(any(Customer.class));

    }

    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 실행에 실패한다.")
    void createCusteomerFail() {
        //given
        CreateCustomerDto dto = new CreateCustomerDto("kim", "won05121@naver.com", 1);
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
        Customer result = new Customer(null, dto.getName(), dto.getEmail(), true);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())).thenReturn(true);

        //when
        customerService.update(1L, dto);

        //then
        verify(customerRepsotiory, atLeast(1)).update(any(Customer.class));
    }

    @Test
    @DisplayName("service의 update()를 통해 repository의 update()의 실행에 실패한다.")
    void updateCustomerFail() {
        //given
        UpdateCustomerDto dto = new UpdateCustomerDto("kim", "won05121@naver.com", 1);
        when(inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())).thenReturn(false);

        //when
        customerService.update(1L,dto);

        //then
        verify(customerRepsotiory, never()).update(any(Customer.class));

    }

    @Test
    @DisplayName("service의 findById()의 인자값에 상관없이 ,repository의 findById()는 반드시 한번은 실행된다.")
    void findByIdCustomerSuccess() {
        //given
        when(customerRepsotiory.findById(any()))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> customerService.findById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
        verify(customerRepsotiory, atLeastOnce()).findById(any());

    }

    @Test
    @DisplayName("service의 findAll()을 통해 repository 의 findAll() 을 실행에 반드시 성공한다.")
    void findAll() {
        //given
        //when
        customerService.findAll();

        //then
        verify(customerRepsotiory, atLeastOnce()).findAll();

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
    @DisplayName("service의 getBlackList()를 통해 repository의 getBlackList()을 반드시 한번은 실행한다.")
    void getBlackList() {
        //given
        //when
        customerService.findBlackList();

        //then
        verify(customerRepsotiory, atLeastOnce()).findBlackList();

    }
}
