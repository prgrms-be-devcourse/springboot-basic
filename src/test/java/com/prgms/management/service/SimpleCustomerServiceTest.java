package com.prgms.management.service;

import com.prgms.management.common.exception.DeleteFailException;
import com.prgms.management.common.exception.EmptyListException;
import com.prgms.management.common.exception.FindFailException;
import com.prgms.management.common.exception.SaveFailException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import com.prgms.management.customer.repository.CustomerRepository;
import com.prgms.management.customer.service.SimpleCustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestClassOrder(ClassOrderer.DisplayName.class)
class SimpleCustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private SimpleCustomerService customerService;

    @DisplayName("addCustomer() : 고객 저장 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class AddCustomerTest {
        @DisplayName("성공 : 레포지토리 단에서 고객 정보가 정상적으로 저장되었습니다.")
        @Test
        void addSuccess() {
            // given
            Customer customer = new Customer("demo", CustomerType.WHITE, "demo@email");
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);
            // when
            Customer result = customerService.addCustomer(customer);
            // then
            assertThat(result, equalTo(customer));
            verify(customerRepository, only()).save(customer);
        }

        @DisplayName("실패 : 레포지토리 단에서 저장에 실패한 경우 SaveFailException 예외가 발생합니다.")
        @Test
        void addFail() {
            // given
            Customer customer = new Customer("demo", CustomerType.WHITE, "demo@email");
            when(customerRepository.save(any(Customer.class))).thenThrow(new SaveFailException());
            // when, then
            assertThrows(SaveFailException.class, () -> customerService.addCustomer(customer));
            verify(customerRepository, only()).save(customer);
        }
    }

    @DisplayName("findAllCustomers() : 고객 목록 조회 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindAllCustomersTest {
        @DisplayName("성공 : 전체 고객 목록을 반환합니다.")
        @Test
        void findSuccess() {
            // given
            List<Customer> expectedList = new ArrayList<>();
            when(customerRepository.findAll()).thenReturn(expectedList);
            // when
            List<Customer> resultList = customerService.findAllCustomers();
            // then
            assertThat(resultList, equalTo(expectedList));
            verify(customerRepository, only()).findAll();
        }

        @DisplayName("실패 : 레포지토리 단에서 조회에 실패한 경우 EmptyListException 예외가 발생합니다.")
        @Test
        void findFail() {
            // given
            when(customerRepository.findAll()).thenThrow(new EmptyListException());
            // when, then
            assertThrows(EmptyListException.class, () -> customerService.findAllCustomers());
            verify(customerRepository, only()).findAll();
        }
    }

    @DisplayName("findCustomerById() : ID로 고객 조회 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class FindCustomerByIdTest {
        @DisplayName("성공 : 레포지토리 단에서 아이디 조회에 성공한 경우 ID에 따른 고객을 반환합니다.")
        @Test
        void findSuccess() {
            // given
            Customer customer = new Customer("demo", CustomerType.WHITE, "demo@email");
            when(customerRepository.findById(any(UUID.class))).thenReturn(customer);
            // when
            Customer result = customerService.findCustomerById(customer.getId());
            // then
            assertThat(result.getId(), is(customer.getId()));
            verify(customerRepository, only()).findById(customer.getId());
        }

        @DisplayName("실패 : 레포지토리 단에서 아이디 조회에 실패한 경우 FindFailException 예외가 발생합니다.")
        @Test
        void findFail() {
            // given
            UUID undefinedId = UUID.randomUUID();
            when(customerRepository.findById(any(UUID.class))).thenThrow(new FindFailException());
            // when, then
            assertThrows(FindFailException.class, () -> customerService.findCustomerById(undefinedId));
            verify(customerRepository, only()).findById(undefinedId);
        }
    }

    @DisplayName("removeVoucherById() : ID로 고객 삭제 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class RemoveCustomerByIdTest {
        @DisplayName("성공 : 레포지토리 단에서 아이디에 따른 고객 삭제에 성공한 경우 반환값이 없습니다.")
        @Test
        void removeSuccess() {
            // given
            Customer customer = new Customer("demo", CustomerType.WHITE, "demo@email");
            doNothing().when(customerRepository).removeById(customer.getId());
            // when
            customerService.removeCustomerById(customer.getId());
            // then
            verify(customerRepository, only()).removeById(customer.getId());
        }

        @DisplayName("실패 : 레포지토리 단에서 아이디에 따른 고객 삭제에 실패한 경우 DeleteFailException 예외가 발생합니다.")
        @Test
        void removeFail() {
            // given
            UUID undefinedId = UUID.randomUUID();
            doThrow(new DeleteFailException()).when(customerRepository).removeById(undefinedId);
            // when, then
            assertThrows(DeleteFailException.class, () -> customerService.removeCustomerById(undefinedId));
            verify(customerRepository, only()).removeById(undefinedId);
        }
    }
}