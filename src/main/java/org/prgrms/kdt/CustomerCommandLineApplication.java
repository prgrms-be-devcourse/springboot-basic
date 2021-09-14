package org.prgrms.kdt;

import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.customer.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerCommandLineApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherCommandLineApplication.class);
        new CustomerTester(applicationContext.getBean(CustomerService.class),
                applicationContext.getBean(CustomerRepository.class)
        ).run();

        applicationContext.close();
    }

}
