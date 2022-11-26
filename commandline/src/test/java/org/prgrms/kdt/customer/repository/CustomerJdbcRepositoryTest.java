package org.prgrms.kdt.customer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.exception.customer.NotFoundCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CustomerJdbcRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("저장소에 Customer를 저장할 수 있다.")
    void insertTest() {

        Customer customer1 = customerRepository.insert("kiwoong", "kiwoong@naver.com");
        Customer customer2 = customerRepository.insert("kimki", "kimki@gmail.com");

        Assertions.assertThat(customerRepository.findById(customer1.getCustomerId())).isEqualTo(customer1);
        Assertions.assertThat(customerRepository.findById(customer2.getCustomerId())).isEqualTo(customer2);
    }

    @Test
    @DisplayName("저장소에서 모든 customer를 가져올 수 있다.")
    void findAllTest() {
        Customer customer1 = customerRepository.insert("kiwoong", "kiwoong@naver.com");
        Customer customer2 = customerRepository.insert("kimki", "kimki@gmail.com");

        List<Customer> results = customerRepository.findAll();

        assertThat(results.size()).isEqualTo(2);
        assertThat(results).contains(customer1, customer2);
    }

    @Test
    @DisplayName("저장소에서 customer를 수정할 수 있다.")
    void updateCorrectTest() {
        Customer customer1 = customerRepository.insert("kiwoong", "kiwoong@naver.com");

        long customerId = customer1.getCustomerId();

        customerRepository.update(customerId, "kiwoong-update", "kiwoong@kakao.com");

        assertThat(customerRepository.findById(customerId).getName()).isEqualTo("kiwoong-update");
    }

    @Test
    @DisplayName("findById에서 잘못된 ID값을 넣어주면 에러가 발생한다.")
    void findByIdIncorrectTest() {
        assertThatThrownBy(() -> {
            customerRepository.findById(999999L);
        }).isInstanceOf(NotFoundCustomerException.class);
    }

    @Test
    @DisplayName("update에서 잘못된 ID값을 넣어주면 에러가 발생한다.")
    void updateIncorrectTest() {

        assertThatThrownBy(() -> {
            customerRepository.update(99999L, "hi", "exception");
        }).isInstanceOf(NotFoundCustomerException.class);
    }
}
