package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import com.dojinyou.devcourse.voucherapplication.customer.dto.CustomerCreateRequest;
import com.dojinyou.devcourse.voucherapplication.customer.entity.CustomerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService 테스트")
class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    private final LocalDateTime testTime = LocalDateTime.of(2022, 5, 13,
                                                            10, 7, 0, 0);

    private final CustomerEntity customer1 = new CustomerEntity(1L, "testCustomer1", testTime, testTime);
    private final CustomerEntity customer2 = new CustomerEntity(2L, "testCustomer2",
                                                                testTime.plusDays(1), testTime.plusDays(1));
    private final List<CustomerEntity> allCustomers = List.of(customer1, customer2);

    @Nested
    @DisplayName("고객 추가 시")
    class 고객_추가_시 {
        @Nested
        @DisplayName("정상적인 데이터가 들어온다면,")
        class 정상적인_데이터가_들어온다면 {

            @Test
            @DisplayName("고객 데이터를 생성 후 반환한다")
            void 고객_데이터를_생성_후_반환한다() {
                // given
                Customer inputCustomer = Customer.from(new CustomerCreateRequest(customer1.getName()));
                when(customerRepository.create(inputCustomer)).thenReturn(Customer.from(customer1));

                // when
                Customer returnCustomer = customerService.create(inputCustomer);

                // then
                assertThat(returnCustomer).isNotNull();
                assertThat(returnCustomer.getName()).isEqualTo(customer1.getName());
                assertThat(returnCustomer.getId()).isNotEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("전체 고객 조회 시")
    class 전체_고객_조회_시 {
        @Nested
        @DisplayName("정상적으로 처리가 된다면")
        class 정상적으로_처리가_된다면 {

            @Test
            @DisplayName("고객 데이터 리스트를 반환한다.")
            void 고객_데이터_리스트를_반환한다() {
                // given
                when(customerRepository.findAll()).thenReturn(allCustomers.stream()
                                                                          .map(Customer::from)
                                                                          .collect(Collectors.toList()));

                // when
                List<Customer> returnCustomers = customerService.findAll();

                // then
                assertThat(returnCustomers).isNotNull();
                assertThat(returnCustomers.size()).isEqualTo(allCustomers.size());
                verify(customerRepository, atLeastOnce()).findAll();


            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 고객 조회 시")
    class id를_이용한_고객_조회_시 {
        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하는 경우,")
        class 해당_id를_가진_고객이_존재하는_경우 {

            @Test
            @DisplayName("해당 id를 가진 고객을 반환한다")
            void 해당_id를_가진_고객을_반환한다() {
                // given
                long inputId = 1;
                when(customerRepository.findById(inputId)).thenReturn(Optional.of(Customer.from(customer1)));

                // when
                Customer customer = customerService.findById(inputId);

                // then
                assertThat(customer).isNotNull();
                assertThat(customer.getId()).isEqualTo(inputId);
            }
        }

        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하지 않는 경우,")
        class 해당_id를_가진_고객이_존재하지_않는_경우 {

            @Test
            @DisplayName("Not Found Exception을 발생시킨다")
            void Not_Found_Exception을_발생시킨다() {
                // given
                long inputId = 10;
                when(customerRepository.findById(inputId)).thenReturn(Optional.empty());

                // when
                Throwable throwable = catchThrowable(() -> customerService.findById(inputId));

                // then
                assertThat(throwable).isNotNull();
                assertThat(throwable).isInstanceOf(NotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 고객 삭제 시")
    class id를_이용한_고객_삭제_시 {

        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하는 경우,")
        class 해당_id를_가진_고객이_존재하는_경우 {

            @Test
            @DisplayName("해당 id를 가진 고객을 삭제한다")
            void 해당_id를_가진_고객을_삭제한다() {
                // given
                long inputId = 1;

                // when
                Throwable throwable = catchThrowable(() -> customerService.deleteById(inputId));

                // then
                verify(customerRepository, atLeastOnce()).deleteById(inputId);
                assertThat(throwable).isNull();
            }
        }

        @Nested
        @DisplayName("해당 id를 가진 고객이 존재하지 않는 경우,")
        class 해당_id를_가진_고객이_존재하지_않는_경우 {

            @Test
            @DisplayName("Not Found Exception을 발생시킨다")
            void Not_Found_Exception을_발생시킨다() {
                // given
                long inputId = 10;
                doThrow(NotFoundException.class).when(customerRepository).deleteById(inputId);

                // when
                Throwable throwable = catchThrowable(() -> customerService.deleteById(inputId));

                // then
                assertThat(throwable).isNotNull();
                assertThat(throwable).isInstanceOf(NotFoundException.class);
            }
        }
    }
}