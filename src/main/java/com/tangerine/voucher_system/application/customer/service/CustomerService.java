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
    private final CustomerServiceMapper mapper;

    public CustomerService(CustomerRepository customerRepository, CustomerServiceMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    public CustomerResult createCustomer(CustomerParam param) {
        return mapper.domainToParam(
                customerRepository.insert(mapper.paramToDomain(param))
        );
    }

    public CustomerResult updateCustomer(CustomerParam param) {
        return mapper.domainToParam(
                customerRepository.update(mapper.paramToDomain(param))
        );
    }

    public List<CustomerResult> findAllCustomers() {
        return mapper.domainsToResults(customerRepository.findAll());
    }

    public List<CustomerResult> findBlackCustomers() {
        return mapper.domainsToResults(customerRepository.findAllBlackCustomers());
    }

    public CustomerResult findCustomerById(UUID customerId) {
        return mapper.domainToParam(
                customerRepository.findById(customerId)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public List<CustomerResult> findCustomerByName(Name name) {
        return mapper.domainsToResults(customerRepository.findByName(name));
    }

    public CustomerResult deleteCustomerById(UUID customerId) {
        Optional<Customer> deletedCustomer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return mapper.domainToParam(
                deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

}
