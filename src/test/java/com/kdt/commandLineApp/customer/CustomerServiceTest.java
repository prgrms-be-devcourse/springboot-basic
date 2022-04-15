package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
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
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @Test
    public void getCustomers() {
        Customer customer1 = null;
        Customer customer2 = null;

        try {
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
        } catch (WrongCustomerParamsException e) {
            e.printStackTrace();
        }
        try {
            assertThat(customerService.getCustomers().get(0),isA(Customer.class));
            assertThat(customerService.getCustomers().get(1),isA(Customer.class));
            assertThat(customerService.getCustomers().get(0),equalToObject(customer1));
            assertThat(customerService.getCustomers().get(1),equalToObject(customer2));
        } catch (Exception e) {

        }
    }
}