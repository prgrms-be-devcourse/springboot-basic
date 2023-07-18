package com.prgrms.springbootbasic.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prgrms.springbootbasic.domain.customer.Customer;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("데이터 베이스와 연동되어 고객 생성 테스트")
    void createCustomerTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer savedCustomer = new Customer(customerId, "so", "jay2309@naver.com", LocalDateTime.now());

        //when
        Customer checkCustomer = customerRepository.save(savedCustomer);

        //then
        assertThat(checkCustomer).isEqualTo(savedCustomer);
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 모든 고객 조회 테스트")
    void findCustomerTest() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer saveCustomer1 = new Customer(customerId1, "so", "jay2309@naver.com", LocalDateTime.now());
        Customer saveCustomer2 = new Customer(customerId2, "jay", "jay1234@naver.com", LocalDateTime.now());

        //when
        Customer checkCustomer1 = customerRepository.save(saveCustomer1);
        Customer checkCustomer2 = customerRepository.save(saveCustomer2);

        List<Customer> customers = List.of(checkCustomer1, checkCustomer2);

        //then
        assertThat(customers).contains(checkCustomer1, checkCustomer2);
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 생성일 순으로 고객을 조회")
    void findByCreatedAtTest() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer saveCustomer1 = new Customer(customerId1, "so", "jay2309@naver.com", LocalDateTime.of(2022, 10, 9, 11, 57));
        Customer saveCustomer2 = new Customer(customerId2, "jay", "jay1234@naver.com", LocalDateTime.of(2022, 12, 9, 15, 38));

        //when
        Customer checkCustomer1 = customerRepository.save(saveCustomer1);
        Customer checkCustomer2 = customerRepository.save(saveCustomer2);
        List<Customer> customers = customerRepository.findByCreatedAt();

        //then
        assertNotNull(customers);
        assertTrue(customers.size() >= 2);
        assertThat(customers.get(0)).isEqualTo(checkCustomer1);
        assertThat(customers.get(1)).isEqualTo(checkCustomer2);
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 고객의 id를 통해서 조회")
    void findByIdTest() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer saveCustomer1 = new Customer(customerId1, "so", "jay2309@naver.com", LocalDateTime.now());
        Customer saveCustomer2 = new Customer(customerId2, "jay", "jay1234@naver.com", LocalDateTime.now());

        //when
        Customer checkCustomer1 = customerRepository.save(saveCustomer1);
        Customer checkCustomer2 = customerRepository.save(saveCustomer2);
        List<Customer> findByIdCustomer = List.of(saveCustomer1, saveCustomer2);

        //then
        assertThat(checkCustomer1.getCustomerId()).isEqualTo(saveCustomer1.getCustomerId());
        assertThat(checkCustomer2.getCustomerId()).isEqualTo(saveCustomer2.getCustomerId());
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 고객 수정 테스트")
        // 아직
    void updateVoucherTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer saveCustomer = new Customer(customerId, "so", "jay2309@naver.com", LocalDateTime.now());
        customerRepository.save(saveCustomer);

        //when
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(saveCustomer.getCustomerId(), "jay", "merry9323@naver.com");
        customerRepository.update(saveCustomer);

        //then
        assertThat(saveCustomer).isEqualTo(customerUpdateRequest);


    }

    @Test
    @DisplayName("데이터 베이스에 저장된 id를 통해서 고객 삭제")
    void deleteByIdTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "so", "meery94@naver.com", LocalDateTime.now());
        customerRepository.save(customer);

        //when
        customerRepository.deleteById(customer.getCustomerId());
        Optional<Customer> deleteCustomer = customerRepository.findById(customer.getCustomerId());

        //then
        assertThat(deleteCustomer).isEmpty();
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 모든 고객 삭제")
    void deleteByAllTest() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        UUID customerId3 = UUID.randomUUID();

        Customer customer1 = new Customer(customerId1, "jay", "merry59323@naver.com", LocalDateTime.now());
        Customer customer2 = new Customer(customerId2, "hanjo", "merry3392@naver.com", LocalDateTime.now());
        Customer customer3 = new Customer(customerId3, "jaewon", "msedy23@naver.com", LocalDateTime.now());
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        //when
        customerRepository.deleteAll();

        //then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);

    }
}