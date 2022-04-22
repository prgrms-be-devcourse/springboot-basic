package org.prgms.kdtspringvoucher.customer.service;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
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

    public List<Customer> showBlackList(){
        List<Customer> blackList = customerRepository.findBlackList();
        blackList.forEach(System.out::println);
        return blackList;
    }

    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }
}
