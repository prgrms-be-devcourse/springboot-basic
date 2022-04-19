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
@ActiveProfiles("db")
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @Test
    public void getCustomers() {

        try {
            Customer customer1 = new Customer("moon","25","man");
            Customer customer2 = new Customer("moon","26","woman");

            var result1 =  customerService.getCustomers().get(0);
            var result2 =  customerService.getCustomers().get(1);

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