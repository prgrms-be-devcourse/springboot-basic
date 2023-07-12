package org.devcourse.springbasic.domain.customer.service;

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
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    public void testSave() {
        //== given ==//
        CustomerDto.SaveRequestDto expectedCustomer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
        //== when ==//
        customerService.save(expectedCustomer);
        //== then ==//
        assertThat(expectedCustomer).isNotNull();
    }

    @Test
    @DisplayName("중복 이메일로 가입할 수 없다.")
    public void testNotDuplicateSave() {

        assertThrows(IllegalArgumentException.class,
                () -> {
                    CustomerDto.SaveRequestDto newCustomer1 = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
                    customerService.save(newCustomer1);
                    CustomerDto.SaveRequestDto newCustomer2 = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerB", "customerA@gmail.com", LocalDateTime.now());
                    customerService.save(newCustomer2);
                });
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다.")
    public void testFindAll() {

        //== given ==//
        int N = 3;
        for (int i = 0; i < N; i++) {
            String name = "customer" + i;
            CustomerDto.SaveRequestDto newCustomer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), name, name + "@gmail.com", LocalDateTime.now());
            customerService.save(newCustomer);
        }

        //== when ==//
        List<CustomerDto.ResponseDto> allCustomer = customerService.findAll();

        //== then ==//
        assertThat(allCustomer.size()).isEqualTo(N);
    }

    @Test
    @DisplayName("Id를 통해 고객정보를 조회할 수 있다.")
    public void testFindById() {

        //== given ==//
        CustomerDto.SaveRequestDto expectedCustomer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
        customerService.save(expectedCustomer);
        Customer.CustomerBuilder expected = Customer.builder()
                .name(expectedCustomer.getName())
                .email(expectedCustomer.getEmail());

        //== when ==//
        CustomerDto.ResponseDto actualCustomer = customerService.findById(expectedCustomer.getCustomerId());
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
        CustomerDto.SaveRequestDto expectedCustomer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
        customerService.save(expectedCustomer);

        //== when ==//
        assertThrows(IllegalArgumentException.class,
                () -> customerService.findById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("e-mail을 통해 고객정보를 조회할 수 있다.")
    public void testFindByEmail() {

        //== given ==//
        CustomerDto.SaveRequestDto expectedCustomer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
        customerService.save(expectedCustomer);
        Customer.CustomerBuilder expected = Customer.builder()
                .name(expectedCustomer.getName())
                .email(expectedCustomer.getEmail());

        //== when ==//
        CustomerDto.ResponseDto actualCustomer = customerService.findByEmail(expectedCustomer.getEmail());
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
        CustomerDto.SaveRequestDto customer = new CustomerDto.SaveRequestDto(UUID.randomUUID(), "customerA", "customerA@gmail.com", LocalDateTime.now());
        customerService.save(customer);
        CustomerDto.UpdateRequestDto expectedCustomer = new CustomerDto.UpdateRequestDto(UUID.randomUUID(), "customerB", "customerB@gmail.com");

        //== when ==//
        UUID actualCustomerId = customerService.update(expectedCustomer);

        //== then ==//
        assertThat(actualCustomerId).isEqualTo(expectedCustomer.getCustomerId());
    }
}