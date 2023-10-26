package com.pgms.part1.domain.customer.service;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.customer.repository.CustomerRepository;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final KeyGenerator keyGenerator;

    public CustomerService(CustomerRepository customerRepository, KeyGenerator keyGenerator) {
        this.customerRepository = customerRepository;
        this.keyGenerator = keyGenerator;
    }

    public List<CustomerResponseDto> listBlockedCustomers(){
        List<Customer> customers = customerRepository.listBlockedCustomers();
        return customers.stream().map(customer ->
                new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked()))
                .collect(Collectors.toList());
    }

    public List<CustomerResponseDto> listCustomers(){
        List<Customer> customers = customerRepository.listCustomers();
        return customers.stream().map(customer ->
                        new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked()))
                .collect(Collectors.toList());
    }

    public void addCustomer(CustomerCreateRequestDto dto){
        Customer customer = new CustomerBuilder().id(keyGenerator.getKey()).name(dto.name())
                .email(dto.email()).build();
        customerRepository.addCustomer(customer);
    }

    public void updateCustomerName(Long id, String name){
        customerRepository.updateCustomerName(id, name);
    }

    public void updateCustomerBlocked(Long id){
        customerRepository.updateCustomerBlocked(id);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteCustomer(id);
    }

    public void listCustomersByWallets(List<Wallet> wallets) {
        customerRepository.listCustomersByWallets(wallets);
    }

    public void isAvailableCustomer(Long id) {
    }
}
