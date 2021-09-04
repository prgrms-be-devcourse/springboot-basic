package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        logger.info("logger name => {}", logger.getName());

        /* Blacklist 과제
        var customerRepository = applicationContext.getBean(CustomerRepository.class);

        System.out.println("customer 목록");
        customerRepository.getCustomers().forEach(customer -> {
            System.out.println(customer.toString());
        });
        System.out.println("blacklist 목록");
        customerRepository.getBlacklist().forEach(blackCustomer -> {
            System.out.println(blackCustomer.toString());
        });
        */
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.info("logger name -> {}", logger.getName());
        logger.info("[OrderProperties] version -> {}", orderProperties.getVersion());
        logger.info("[OrderProperties] minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.info("[OrderProperties] supportVendors -> {}", orderProperties.getSupportVendors());
        logger.info("[OrderProperties] description -> {}", orderProperties.getDescription());
    }
}
