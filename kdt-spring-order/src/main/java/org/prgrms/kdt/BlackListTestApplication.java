package org.prgrms.kdt;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.repository.MemoryCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BlackListTestApplication {
    private static final Logger logger = LoggerFactory.getLogger(BlackListTestApplication.class);

    public static void main(String[] args) {
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("local");

        ConfigurableApplicationContext applicationContext = springApplication.run();

        MemoryCustomerRepository customerRepository = applicationContext.getBean(MemoryCustomerRepository.class);

        try {
            showBlackList(customerRepository.findBlackList());
        }catch (Exception e){
            logger.error(e.getMessage());
        }

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
