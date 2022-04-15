package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

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
            Customer customer1 = null;
            Customer customer2 = null;

            customer1 = new Customer(
                    new ArrayList<>(){{
                        add("moon");
                        add("25");
                        add("man");
                        add("X");
                    }}
            );
            customer2 = new Customer(
                    new ArrayList<>(){{
                        add("moon");
                        add("26");
                        add("woman");
                        add("X");
                    }}
            );

            assertThat(customerRepository.getAll().get(0),isA(Customer.class));
            assertThat(customerRepository.getAll().get(1),isA(Customer.class));
            assertThat(customerRepository.getAll().get(0),equalToObject(customer1));
            assertThat(customerRepository.getAll().get(1),equalToObject(customer2));
        }
        catch (Exception e) {

        }
    }
}