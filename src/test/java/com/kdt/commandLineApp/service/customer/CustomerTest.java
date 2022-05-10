package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import com.kdt.commandLineApp.service.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("db")
class CustomerTest {
    @Test
    public void testCreateCustomer1() {
        try {
            Customer customer = new Customer("moon","25","man");

            assertThat(customer.getName(), is("moon"));
            assertThat(customer.getAge(), is(25));
            assertThat(customer.getSex(), is("man"));
        }
        catch (WrongCustomerParamsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateCustomer2() {
        assertThrows(WrongCustomerParamsException.class, ()->{
            new Customer("moon","25","mbn");
        });
        assertThrows(WrongCustomerParamsException.class, ()->{
            new Customer("moon","-26","woman");
        });
    }
}