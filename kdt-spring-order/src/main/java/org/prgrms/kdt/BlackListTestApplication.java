package org.prgrms.kdt;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BlackListTestApplication {
    public static void main(String[] args) {
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("local");

        ConfigurableApplicationContext applicationContext = springApplication.run();

        CustomerRepository customerRepository = applicationContext.getBean(CustomerRepository.class);
        showBlackList(customerRepository.findBlackList());

        applicationContext.close();
    }

    private static void showBlackList(List<Customer> blackList){
        System.out.println("===== Customer Blacklist =====");
        for(Customer customer : blackList){
            System.out.println(customer);
        }
        System.out.println("==============================");
    }
}
