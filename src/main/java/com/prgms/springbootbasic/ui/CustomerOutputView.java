package com.prgms.springbootbasic.ui;

import com.prgms.springbootbasic.domain.Customer;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerOutputView {

    private static final TextTerminal textTerminal = TextIoFactory.getTextTerminal();
    private static final String FORMAT_CUSTOMER = "customer Id : %s name : %s";

    public void initApplication() {
        textTerminal.println("=== Customer Application ===");
        textTerminal.println("Type list to black list");
    }

    private String changeCustomerToString(Customer customer) {
        UUID customerId = customer.getCustomerId();
        String name = customer.getName();
        return String.format(FORMAT_CUSTOMER, customerId, name);
    }

    public List<String> changeCustomerToString(List<Customer> customers) {
        return customers.stream()
                .map(this::changeCustomerToString)
                .toList();
    }

}
