package com.example.demo.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.customer.Customer;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객을 생성하여 데이터베이스에 저장할 수 있다.")
    void save() {
        // given
        Customer customerExpect = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        // when
        Customer customerActual = customerRepository.save(customerExpect);

        // then
        assertThat(customerActual).isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("고객을 저장한 뒤, 저장한 고객의 ID로 조회하여 고객을 가져올 수 있다.")
    void findById() {
        // given
        Customer customerExpect = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        // when
        customerRepository.save(customerExpect);

        Customer customerActual = customerRepository.findById(customerExpect.getId())
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다."));

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("여러 고객을 저장한 뒤, 저장된 모든 고객을 조회할 수 있다.")
    void findAll() {
        // given
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyata")
                .age(30)
                .build();

        List<Customer> customerListExpect = List.of(
                customer1,
                customer2
        );

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customerListActual = customerRepository.findAll();

        // then
        assertThat(customerListActual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(customerListExpect);
    }


    @Test
    @DisplayName("데이터베이스에 저장된 고객의 이름 업데이트 할 수 있다.")
    void update() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        String newName = "cha";

        Customer customerExpect = Customer.builder()
                .id(customer.getId())
                .name(newName)
                .age(27)
                .build();

        // when
        customerRepository.save(customer);
        customerRepository.updateName(customer.getId(), newName);
        Customer customerActual = customerRepository.findById(customerExpect.getId())
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다."));

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("고객을 저장하고 저장한 고객의 ID를 데이터베이스에서 찾아 삭제할 수 있다.")
    void deleteById() {
        // given
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("hyunho")
                .age(27)
                .build();

        // when
        customerRepository.save(customer);
        customerRepository.deleteById(customer.getId());
        Optional<Customer> customerActual = customerRepository.findById(customer.getId());

        // then
        assertThat(customerActual).isEmpty();
    }
}