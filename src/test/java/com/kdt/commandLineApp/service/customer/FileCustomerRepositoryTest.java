package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.customer.CustomerRepository;
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
            Customer customer1 = new Customer("moon","25","man");
            Customer customer2 = new Customer("moon","26","woman");

            Customer result1 = customerRepository.getAllBlacklist().get(0);
            Customer result2 = customerRepository.getAllBlacklist().get(1);

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