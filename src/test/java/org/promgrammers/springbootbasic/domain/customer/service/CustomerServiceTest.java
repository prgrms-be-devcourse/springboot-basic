package org.promgrammers.springbootbasic.domain.customer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.customer.dto.request.CreateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.request.UpdateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CustomerServiceTest {

    @Autowired
    private JdbcCustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("저장 성공- 고객 저장 테스트")
    void save() throws Exception {

        //given
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest("A");

        //when
        CustomerResponse customerResponse = customerService.createCustomer(createCustomerRequest);
        //then
        assertTrue(customerRepository != null);
        assertThat(customerRepository.findAll().get(0).getCustomerId()).isEqualTo(customerResponse.customerId());
    }

    @Test
    @DisplayName("고객 생성 실패 - 이미 존재하는 사용자명")
    void createCustomerFailTest_DuplicatedUsername() {
        // given
        String existingUsername = "A";
        customerRepository.save(new Customer(UUID.randomUUID(), existingUsername));

        // when -> then
        assertThrows(BusinessException.class, () -> {
            CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(existingUsername);
            customerService.createCustomer(createCustomerRequest);
        });
    }

    @Test
    @DisplayName("고객 생성 실패 - 잘못된 사용자명")
    void createCustomerFailTest_InvalidUsername() {
        // given
        String existingUsername = "!@#$";
        customerRepository.save(new Customer(UUID.randomUUID(), existingUsername));

        // when -> then
        assertThrows(BusinessException.class, () -> {
            CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(existingUsername);
            customerService.createCustomer(createCustomerRequest);
        });
    }

    @Test
    @DisplayName("단건 조회 성공 - 아이디가 존재하는 경우")
    void findOneByCustomerIdSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        customerRepository.save(customer);

        //when
        CustomerResponse findCustomer = customerService.findCustomerById(customer.getCustomerId());

        //then
        assertThat(findCustomer.customerId()).isEqualTo(customer.getCustomerId());
        assertThat(findCustomer.username()).isEqualTo(customer.getUsername());
    }

    @Test
    @DisplayName("단건 조회 실패 - 아이디가 존재하지 않는 경우")
    void findOneByCustomerIdFailTest() throws Exception {

        // given
        UUID customerId = UUID.randomUUID();

        // when -> then
        assertThrows(BusinessException.class, () -> customerService.findCustomerById(customerId));
    }

    @Test
    @DisplayName("단건 조회 성공 - Username이 존재하는 경우")
    void findCustomerByUsernameSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        customerRepository.save(customer);

        //when
        CustomerResponse findCustomer = customerService.findCustomerByUsername(customer.getUsername());

        //then
        assertThat(findCustomer.customerId()).isEqualTo(customer.getCustomerId());
        assertThat(findCustomer.username()).isEqualTo(customer.getUsername());
    }

    @Test
    @DisplayName("단건 조회 실패 - Username이 존재하지 않는 경우")
    void findCustomerByUsernameFailTest() throws Exception {

        // given
        String username = "B";

        // when -> then
        assertThrows(BusinessException.class, () -> customerService.findCustomerByUsername(username));
    }

    @Test
    @DisplayName("전체 조회 - 고객 전체 조회")
    void findAllCustomersSuccessTest() throws Exception {

        //given
        CreateCustomerRequest request1 = new CreateCustomerRequest("A");
        CreateCustomerRequest request2 = new CreateCustomerRequest("B");
        List<CreateCustomerRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(customerService::createCustomer);

        //when
        CustomersResponse customerList = customerService.findAllCustomers();

        //then
        assertNotNull(customerList);
        List<CustomerResponse> customerResponseList = customerList.customers();
        assertThat(customerResponseList).hasSize(requestList.size());

        CustomerResponse firstResponse = customerResponseList.get(0);
        assertThat(firstResponse.username()).isEqualTo(request1.username());

        CustomerResponse secondResponse = customerResponseList.get(1);
        assertThat(secondResponse.username()).isEqualTo(request2.username());
    }

    @Test
    @DisplayName("업데이트 - 고객 업데이트 성공")
    void updateCustomerSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        customerRepository.save(customer);

        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(customer.getCustomerId(), "B", customer.getCustomerType());

        //when
        CustomerResponse customerResponse = customerService.updateCustomer(updateCustomerRequest);

        //then
        assertNotNull(customerResponse);
        assertThat(customerId).isEqualTo(customerResponse.customerId());
        assertThat(username).isNotSameAs(customerResponse.username());
    }

    @Test
    @DisplayName("전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        CreateCustomerRequest request1 = new CreateCustomerRequest("A");
        CreateCustomerRequest request2 = new CreateCustomerRequest("B");
        List<CreateCustomerRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(customerService::createCustomer);

        //when
        customerService.deleteAllCustomers();

        //then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("단건 삭제 성공 - 고객ID가 존재하는 경우")
    void deleteCustomerByIdSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        customerRepository.save(customer);

        //when
        assertDoesNotThrow(() -> customerService.deleteById(customerId));
        Optional<Customer> deletedCustomer = customerRepository.findById(customerId);

        //then
        assertThat(deletedCustomer.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("단건 삭제 실패 - 고객ID가 존재하지 않는 경우")
    void deleteCustomerByIdFailTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();

        assertThrows(BusinessException.class, () -> customerService.deleteById(customerId));
    }
}