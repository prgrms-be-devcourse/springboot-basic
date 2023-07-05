package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.io.CsvReader;
import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void init() {
        customerRepository.setFilePath("storage/customers/customer_blacklist.csv");
    }

    @Test
    @DisplayName("블랙고객 리스트를 반환하면 성공한다.")
    void FindAllBlackCustomers_Normal_ReturnBlackCustomers() {
        var result = customerRepository.findAllBlackCustomers();
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Customer.class));
    }

}