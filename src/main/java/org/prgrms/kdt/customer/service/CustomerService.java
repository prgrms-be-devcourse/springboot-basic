package org.prgrms.kdt.customer.service;

import java.time.LocalDateTime;
import java.util.*;
import org.modelmapper.ModelMapper;
import org.prgrms.kdt.customer.controller.CustomerDto;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.customer.model.CustomerType;
import org.prgrms.kdt.exception.BadRequestException;
import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.utils.EnumUtils;
import org.prgrms.kdt.utils.MappingUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository,
        ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDto getCustomer(UUID customerId) {
        var customer = customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new NotFoundException("Can not find customer %s".formatted(customerId)));
        return modelMapper.map(customer, CustomerDto.class);
    }

    public List<CustomerDto> getAllCustomers() {
        return MappingUtils
            .mapList(customerRepository.findAllCustomer(), CustomerDto.class, modelMapper);

    }

    public List<CustomerDto> getAllBlackCustomers() {
        return MappingUtils
            .mapList(
                customerRepository.findByCustomerType(CustomerType.BLACK),
                CustomerDto.class,
                modelMapper);
    }

    public List<CustomerDto> getCustomersByVoucherId(UUID voucherId) {
        return MappingUtils
            .mapList(
                customerRepository.findByVoucherId(voucherId),
                CustomerDto.class,
                modelMapper);
    }

    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        var customer = customerRepository.insert(
            new Customer(
                UUID.randomUUID(),
                customerDto.getName(),
                customerDto.getEmail(),
                LocalDateTime.now()));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Transactional
    public CustomerDto updateCustomer(UUID customerId, CustomerDto customerDto) {
        var customer = customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new NotFoundException(
                    "Can not find customer %s".formatted(customerId)));
        customer.changeCustomerType(
            EnumUtils.getCustomerTypeByName(customerDto.getCustomerType())
                .orElseThrow(() -> new BadRequestException("invalid customer type")));
        customer.changeName(customerDto.getName());

        var updatedCustomer = customerRepository.update(customer);
        return modelMapper.map(updatedCustomer, CustomerDto.class);
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
