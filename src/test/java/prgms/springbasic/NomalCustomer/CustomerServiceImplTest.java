package prgms.springbasic.NomalCustomer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void join() {
        Customer newCustomer = new Customer(UUID.randomUUID(), "hello", "hello@gmail.com");
        Customer joinCustomer = customerService.join(newCustomer);
        Assertions.assertThat(joinCustomer.getEmail()).isEqualTo(newCustomer.getEmail());
    }
}