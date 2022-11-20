package prgms.vouchermanagementapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.vouchermanagementapp.domain.Customer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerManagerTest {

    @Autowired
    private CustomerManager customerManager;

    @DisplayName("이름을 기반으로 고객을 저장할 수 있다.")
    @Test
    void save() {
        // given
        Customer customer1 = new Customer("test1");

        // when
        Customer savedCustomer = customerManager.save(customer1);

        // then
        assertThat(savedCustomer.getCustomerName()).isEqualTo(customer1.getCustomerName());
        System.out.println(savedCustomer.getCustomerName());
    }

    @DisplayName("DB에 저장된 이름을 조회할 수 있다.")
    @Test
    void findCustomerByName() {
        // given
        Customer savedCustomer1 = customerManager.save(new Customer("test1"));

        // when
        Optional<Customer> optionalCustomer = customerManager.findCustomerByName("test1");
        assert optionalCustomer.isPresent();

        // then
        assertThat(optionalCustomer.get().getCustomerName()).isEqualTo(savedCustomer1.getCustomerName());
    }
}