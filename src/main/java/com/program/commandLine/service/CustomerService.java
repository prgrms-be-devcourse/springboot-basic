package com.program.commandLine.service;

import com.program.commandLine.customer.BlackListCustomer;
import com.program.commandLine.customer.Customer;
import com.program.commandLine.customer.CustomerFactory;
import com.program.commandLine.customer.CustomerType;
import com.program.commandLine.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final ApplicationContext applicationContext;
    private final CustomerFactory customerFactory;
    private final CustomerRepository customerRepository;

    private final List<Customer> blackListConsumers = new ArrayList<>();

    @Value("${customer.black_list.path}")
    private String blackListFilePath;

    public CustomerService(ApplicationContext applicationContext, CustomerFactory customerFactory, CustomerRepository customerRepository) {
        this.applicationContext = applicationContext;
        this.customerFactory = customerFactory;
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    private void readBlackListFile() {
        Resource resource = applicationContext.getResource(blackListFilePath);
        if (!resource.exists()) return;
        try {
            Files.readAllLines(resource.getFile().toPath()).forEach(value -> {
                String[] customerInfo = value.split(" ");
                blackListConsumers.add(new BlackListCustomer(UUID.randomUUID(), customerInfo[0], customerInfo[1], LocalDateTime.now()));
            });
        } catch (IOException error) {
            throw new RuntimeException("! Failed to open Blacklist file");
        }
    }

    public List<Customer> getBlackListCustomers() {
        return List.copyOf(blackListConsumers);
    }

    public Customer createCustomer(String stringCustomerType,UUID customerId, String name, String email){
        CustomerType customerType = CustomerType.getType(stringCustomerType);
        return customerFactory.createCustomer(customerType,customerId,name,email);
    }
}
