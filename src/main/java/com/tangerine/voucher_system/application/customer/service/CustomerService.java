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
        return CustomerServiceMapper.INSTANCE.domainsToResults(customerRepository.findAll());
    }

    public List<CustomerResult> findBlackCustomers() {
        return CustomerServiceMapper.INSTANCE.domainsToResults(customerRepository.findAllBlackCustomers());
    }

    public CustomerResult findCustomerById(UUID customerId) {
        return CustomerServiceMapper.INSTANCE.domainToParam(
                customerRepository.findById(customerId)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public List<CustomerResult> findCustomerByName(Name name) {
        return CustomerServiceMapper.INSTANCE.domainsToResults(customerRepository.findByName(name));
    }

    public CustomerResult deleteCustomerById(UUID customerId) {
        Optional<Customer> deletedCustomer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);
        return CustomerServiceMapper.INSTANCE.domainToParam(
                deletedCustomer.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

}
