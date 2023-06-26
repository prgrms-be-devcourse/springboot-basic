package org.devcourse.springbasic.domain.customer.dao;

import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    public void testSave() {
        //== given ==//
        Customer expectedCustomer = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());        //== when ==//
        customerJdbcRepository.save(expectedCustomer);
        //== then ==//
        assertThat(expectedCustomer).isNotNull();
    }

    @Test
    @DisplayName("중복 이메일로 가입할 수 없다.")
    public void testNotDuplicateSave() {

        assertThrows(IllegalArgumentException.class,
                () -> {
                    Customer newCustomer1 = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                    customerJdbcRepository.save(newCustomer1);
                    Customer newCustomer2 = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
                    customerJdbcRepository.save(newCustomer2);
                });
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    public void testFindAll() {
        //== given ==//
        int N = 3;
        for (int i = 0; i < N; i++) {
            String name = "customer" + i;
            Customer newCustomer = new Customer(UUID.randomUUID(), name, name + "@gmail.com", LocalDateTime.now(), LocalDateTime.now());
            customerJdbcRepository.save(newCustomer);
        }

        //== when ==//
        List<Customer> allCustomer = customerJdbcRepository.findAll();

        //== then ==//
        assertThat(allCustomer.size()).isEqualTo(N);
    }


    @Test
    @DisplayName("Id를 통해 고객정보를 조회할 수 있다.")
    public void testFindById() {

        //== given ==//
        Customer expectedCustomer = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.save(expectedCustomer);
        Customer.CustomerBuilder expected = Customer.builder()
                .name(expectedCustomer.getName())
                .email(expectedCustomer.getEmail());

        //== when ==//
        Customer actualCustomer = customerJdbcRepository.findById(expectedCustomer.getCustomerId());
        Customer.CustomerBuilder actual = Customer.builder()
                .name(actualCustomer.getName())
                .email(actualCustomer.getEmail());

        //== then ==//
        assertThat(expected)
                .usingRecursiveComparison()
                .isEqualTo(actual);
    }

    @Test
    @DisplayName("없는 Id는 조회에 실패한다.")
    public void testNotFoundById() {
        //== given ==//
        Customer expectedCustomer = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.save(expectedCustomer);

        //== when ==//
        assertThrows(IllegalArgumentException.class,
                () -> customerJdbcRepository.findById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("e-mail을 통해 고객정보를 조회할 수 있다.")
    public void testFindByEmail() {

        //== given ==//
        Customer expectedCustomer = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.save(expectedCustomer);
        Customer.CustomerBuilder expected = Customer.builder()
                .name(expectedCustomer.getName())
                .email(expectedCustomer.getEmail());

        //== when ==//
        Customer actualCustomer = customerJdbcRepository.findByEmail(expectedCustomer.getEmail());
        Customer.CustomerBuilder actual = Customer.builder()
                .name(actualCustomer.getName())
                .email(actualCustomer.getEmail());

        //== then ==//
        assertThat(expected)
                .usingRecursiveComparison()
                .isEqualTo(actual);
    }

    @Test
    @DisplayName("고객 정보를 업데이트 할 수 있다.")
    public void testUpdate(){

        //== given ==//
        Customer customer = new Customer(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerJdbcRepository.save(customer);
        CustomerDto.UpdateRequestDto updateRequestDto = new CustomerDto.UpdateRequestDto(UUID.randomUUID(), "customerB", "customerB@gmail.com");
        Customer updateCustomer = new Customer(updateRequestDto.getCustomerId(), updateRequestDto.getName(), updateRequestDto.getEmail(), LocalDateTime.now(), LocalDateTime.now());

        //== when ==//
        UUID actualCustomerId = customerJdbcRepository.update(updateCustomer);

        //== then ==//
        assertThat(actualCustomerId).isEqualTo(updateCustomer.getCustomerId());
    }
}