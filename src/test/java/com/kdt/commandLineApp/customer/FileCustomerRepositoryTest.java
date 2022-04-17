package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.isA;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("dev")
class FileCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testGetAll() {
        try {
            var customer1 = new Customer("moon","25","man");
            var customer2 = new Customer("moon","26","woman");

            var result1 = customerRepository.getAllBlacklist().get(0);
            var result2 = customerRepository.getAllBlacklist().get(1);

            assertThat(result1, isA(Customer.class));
            assertThat(result2, isA(Customer.class));
            assertThat(result1, equalToObject(customer1));
            assertThat(result2, equalToObject(customer2));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}