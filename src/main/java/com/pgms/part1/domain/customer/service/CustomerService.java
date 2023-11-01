package com.pgms.part1.domain.customer.service;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.customer.repository.CustomerRepository;
import com.pgms.part1.exception.ErrorCode;
import com.pgms.part1.exception.VoucherApplicationException;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void isEmailDuplicated(String email){
        if(customerRepository.findCustomerByEmail(email) > 0)
            throw new VoucherApplicationException(ErrorCode.CUSTOMER_ALREADY_EXIST);
    }

    public void isCustomerExist(Long id){
        customerRepository.findCustomerById(id).orElseThrow(() -> new VoucherApplicationException(ErrorCode.CUSTOMER_NOT_EXIST));
    }

    public void isEmailNull(String email){
        if(email == null)
            throw new VoucherApplicationException(ErrorCode.INVALID_INPUT_DATA);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> listBlockedCustomers(){
        List<Customer> customers = customerRepository.listBlockedCustomers();
        return customers.stream().map(customer ->
                new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> listCustomers(){
        List<Customer> customers = customerRepository.listCustomers();
        return customers.stream().map(customer ->
                        new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked()))
                .toList();
    }

    public Customer addCustomer(CustomerCreateRequestDto dto){
        isEmailNull(dto.email());
        isEmailDuplicated(dto.email());
        Customer customer = new CustomerBuilder().id(keyGenerator.getKey()).name(dto.name())
                .email(dto.email()).build();
        customerRepository.addCustomer(customer);
        log.info("customer created -> {}", customer.getId());
        return customer;
    }

    public void updateCustomerName(Long id, String name){
        customerRepository.updateCustomerName(id, name);
        log.info("{} customer name updated", id);
    }

    public void updateCustomerBlocked(Long id){
        isCustomerExist(id);
        customerRepository.updateCustomerBlocked(id);
        log.info("{} customer blocked updated", id);
    }

    public void deleteCustomer(Long id){
        isCustomerExist(id);
        customerRepository.deleteCustomer(id);
        log.info("{} customer deleted", id);
    }
}
