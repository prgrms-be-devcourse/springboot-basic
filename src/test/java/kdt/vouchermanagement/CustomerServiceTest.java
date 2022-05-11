package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.dto.CustomerResponse;
import kdt.vouchermanagement.domain.customer.entity.CustomerEntity;
import kdt.vouchermanagement.domain.customer.repository.CustomerRepository;
import kdt.vouchermanagement.domain.customer.service.CustomerService;
import kdt.vouchermanagement.domain.customer.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void customer_저장_요청이_들어올_때_전달받은_customer가_null이면_IllegalStateException이_발생한다() {
        //given
        Customer customer = null;

        //when, then
        assertThrows(IllegalStateException.class, () -> customerService.saveCustomer(customer));
    }

    @Test
    void customer_저장_요청이_들어올_때_정상적인_요청_데이터라면_customerRepository의_save_메소드를_호출한다() {
        //given
        Customer customer = firstCustomer();

        //when
        customerService.saveCustomer(customer);

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void customer_목록_조회_요청이_들어올_때_customer_목록을_조회하고_customer_DTO_리스트를_반환한다() {
        //given
        Customer firstCustomer = firstCustomer();
        Customer secondCustomer = secondCustomer();
        List<Customer> customers = List.of(firstCustomer, secondCustomer);
        List<CustomerResponse> customerResponses = CustomerResponse.listOf(customers);
        doReturn(customers).when(customerRepository).findAll();

        //when
        List<CustomerResponse> foundCustomerResponses = customerService.findAllCustomers();

        //then
        verify(customerRepository, times(1)).findAll();
        assertThat(foundCustomerResponses).usingRecursiveComparison().isEqualTo(customerResponses);
    }

    @Test
    void customer_삭제_요청이_들어올_때_파라미터로_넘어온_id_값을_가진_customer가_존재한다면_IllegalArgumentException이_발생한다() {
        //given
        long id = 1L;
        doReturn(Optional.empty()).when(customerRepository).findById(id);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> customerService.deleteById(id));
    }

    @Test
    void customer_삭제_요청이_들어올_때_정상적인_요청_데이터라면_customerRepository의_deleteById_메소드를_호출한다() {
        //given
        long id = 1L;
        doReturn(Optional.of(Customer.class)).when(customerRepository).findById(id);

        //when
        customerService.deleteById(id);

        //then
        verify(customerRepository, times(1)).deleteById(id);
    }

    private Customer firstCustomer() {
        return CustomerEntity.of(
                1L,
                "first test",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
    }

    private Customer secondCustomer() {
        return CustomerEntity.of(
                2L,
                "second test",
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        ).toDomain();
    }
}
