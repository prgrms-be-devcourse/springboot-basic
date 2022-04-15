package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("dev")
class CustomerTest {
    @Test
    public void testCreateCustomer1() {
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
                        add("25");
                        add("mefsdn");
                        add("X");
                    }}
            );

            assertThat(customer1.getName(), is("moon"));
            assertThat(customer1.getAge(), is(25));
            assertThat(customer1.getSex(), is("man"));
            assertThat(customer1.getDescription(), is("X"));
        }
        catch (WrongCustomerParamsException e) {
        }
    }

    @Test
    public void testCreateCustomer2() {
        assertThrows(WrongCustomerParamsException.class, ()->{
            new Customer(
                    new ArrayList<>(){{
                        add("moon");
                        add("25");
                        add("mefsdn");
                        add("X");
                    }}
            );
        });
        assertThrows(WrongCustomerParamsException.class, ()->{
            new Customer(
                    new ArrayList<>(){{
                        add("moon");
                        add("-25");
                        add("man");
                        add("X");
                    }}
            );
        });
    }
}