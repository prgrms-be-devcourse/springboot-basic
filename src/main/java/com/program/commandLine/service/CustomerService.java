package com.program.commandLine.service;

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
                blackListConsumers.add(customerFactory.createCustomer(CustomerType.BLACK_LIST_CUSTOMER,UUID.randomUUID(), customerInfo[0], customerInfo[1]));
            });
        } catch (IOException error) {
            throw new RuntimeException("!! 블랙리스트 파일 오픈을 실패하였습니다.");
        }
    }

    public List<Customer> getBlackListCustomers() {
        return List.copyOf(blackListConsumers);
    }

    public Customer createCustomer(String stringCustomerType,UUID customerId, String name, String email){
        CustomerType customerType = CustomerType.getType(stringCustomerType);
        Customer newCustomer = customerFactory.createCustomer(customerType,customerId,name,email);
        return customerRepository.insert(newCustomer);
    }

    public Customer getCustomerByName(String customerName) {
        return customerRepository.findByName(customerName).orElseThrow(()-> new IllegalArgumentException("!! 존재하지 않는 고객입니다."));
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(()-> new IllegalArgumentException("!! 존재하지 않는 고객입니다."));
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
