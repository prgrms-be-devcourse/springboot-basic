package me.kimihiqq.vouchermanagement.domain.customer.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final String CUSTOMER_LIST_FILE = "src/main/resources/customer_list.csv";
    private Map<String, Customer> customers = new HashMap<>();

    @PostConstruct
    public void init() {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_LIST_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                customers.put(values[0], new Customer(values[0], values[1], CustomerStatus.valueOf(values[2].toUpperCase())));
            }
        } catch (Exception e) {
            log.error("Error occurred while initializing customer data", e);
        }
    }

    public Customer findCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        log.info("Customer found with ID {}: {}", customerId, customer);
        return customer;
    }

    public List<Customer> findBlacklistedCustomers() {
        List<Customer> blacklistedCustomers = customers.values().stream()
                .filter(customer -> customer.getStatus() == CustomerStatus.BLACK)
                .collect(Collectors.toList());
        log.info("Found {} blacklisted customers", blacklistedCustomers.size());
        return blacklistedCustomers;
    }
}