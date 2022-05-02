package org.prgms.kdtspringvoucher.customer.service;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String email, CustomerType customerType) {
        return customerRepository.save(new Customer(UUID.randomUUID(), name, email, customerType, LocalDateTime.now()));
    }

    public Customer changeCustomerTypeById(Customer customer, CustomerType updateCustomerType) {
        Customer updateCustomer = customerRepository.findById(customer.getCustomerId()).get();
        updateCustomer.changeCustomerType(updateCustomerType);
        return customerRepository.update(updateCustomer);
    }

    public List<Customer> showAllCustomer(){
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> System.out.println((customers.indexOf(customer) + 1) + ". " + customer));
        return customers;
    }

    public void showBlackList(CustomerType customerType){
        customerRepository.findByCustomerType(customerType).forEach(System.out::println);
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer updateCustomerById(UUID customerId, String name, CustomerType customerType) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        customer.changeName(name);
        customer.changeCustomerType(customerType);
        return customerRepository.update(customer);
    }

    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
