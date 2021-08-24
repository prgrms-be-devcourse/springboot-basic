package com.org.prgrms.mission.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.ConsumerType;
import com.org.prgrms.mission.model.Customer;
import com.org.prgrms.mission.repository.CustomerRepository;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final String BlackList_FilePath = "./blackList.csv";
    private final String CustomerList_FilePath = "./customerList.csv";

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, int age, ConsumerType consumerType) {
        Customer customer = new Customer(UUID.randomUUID(), name, age, consumerType);

        return customer;
    }
    public Customer saveCustomer(Customer customer) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        customerRepository.insert(customer, CustomerList_FilePath);
        if(customer.getConsumerType().equals(ConsumerType.BLACK_CONSUMER))
            customerRepository.insert(customer, BlackList_FilePath);
        return customer;
    }

    public List<Customer> findAll(String listType) {
        if (listType.equals("Blacklist"))
            return customerRepository.ShowList(BlackList_FilePath);
        else
            return customerRepository.ShowList(CustomerList_FilePath);
    }
}

