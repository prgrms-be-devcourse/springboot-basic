package com.tangerine.voucher_system.application.customer.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.customer.service.mapper.CustomerServiceMapper;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResult> findBlackCustomers() {
        return customerRepository.findAllBlackCustomers()
                .stream()
                .map(CustomerServiceMapper.INSTANCE::domainToParam)
                .toList();
    }

    public CustomerResult createCustomer(CustomerParam param) {
        return CustomerServiceMapper.INSTANCE.domainToParam(
                customerRepository.insert(CustomerServiceMapper.INSTANCE.paramToDomain(param))
        );
    }

    public CustomerResult updateCustomer(CustomerParam param) {
        return CustomerServiceMapper.INSTANCE.domainToParam(
                customerRepository.update(CustomerServiceMapper.INSTANCE.paramToDomain(param))
        );
    }

    public List<CustomerResult> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerServiceMapper.INSTANCE::domainToParam)
                .toList();
    }

    public CustomerResult findCustomerById(UUID customerId) {
        return CustomerServiceMapper.INSTANCE.domainToParam(
                customerRepository.findById(customerId)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public CustomerResult findCustomerByName(Name name) {
        return CustomerServiceMapper.INSTANCE.domainToParam(
                customerRepository.findByName(name)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public CustomerResult deleteCustomerById(UUID customerId) {
        Optional<Customer> deletedCustomer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return CustomerServiceMapper.INSTANCE.domainToParam(
                deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

}
