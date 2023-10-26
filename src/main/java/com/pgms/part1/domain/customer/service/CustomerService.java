package com.pgms.part1.domain.customer.service;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.customer.repository.CustomerRepository;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final Logger log = LoggerFactory.getLogger(CustomerService.class);
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
        try{
            Customer customer = new CustomerBuilder().id(keyGenerator.getKey()).name(dto.name())
                    .email(dto.email()).build();
            customerRepository.addCustomer(customer);
            log.info("customer created -> {}", customer.getId());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void updateCustomerName(Long id, String name){
        try{
            customerRepository.updateCustomerName(id, name);
            log.info("{} customer name updated");
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void updateCustomerBlocked(Long id){
        try{
            customerRepository.updateCustomerBlocked(id);
            log.info("{} customer blocked updated", id);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void deleteCustomer(Long id){
        try{
            customerRepository.deleteCustomer(id);
            log.info("{} customer deleted", id);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public List<CustomerResponseDto> listCustomersByWallets(List<Wallet> wallets) {
        return customerRepository.listCustomersByWallets(wallets).stream().map(customer ->
                        new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked()))
                .collect(Collectors.toList());
    }

    // todo 있는지 체크
    public void isAvailableCustomer(Long id) {
    }
}
