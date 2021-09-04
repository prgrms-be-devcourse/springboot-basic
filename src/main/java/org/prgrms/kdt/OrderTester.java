package org.prgrms.kdt;

import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerRepository = applicationContext.getBean(CustomerRepository.class);

        System.out.println("customer 목록");
        customerRepository.getCustomers().forEach(customer -> {
            System.out.println(customer.toString());
        });
        System.out.println("blacklist 목록");
        customerRepository.getBlacklist().forEach(blackCustomer -> {
            System.out.println(blackCustomer.toString());
        });

    }
}
