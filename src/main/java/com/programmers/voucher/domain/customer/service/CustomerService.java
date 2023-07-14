package com.programmers.voucher.domain.customer.service;

import com.programmers.voucher.domain.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.customer.dto.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.entity.Customer;
import com.programmers.voucher.domain.customer.repository.CustomerRepository;
import com.programmers.voucher.exception.ConflictException;
import com.programmers.voucher.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.constant.ErrorCode.EXISTED_NICKNAME;
import static com.programmers.voucher.constant.ErrorCode.NOT_FOUND_CUSTOMER;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        validateDuplicate(customerRepository.findByNickname(request.nickname()));
        Customer customer = customerRepository.insert(request.toEntity());

        return CustomerResponse.from(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    public CustomerResponse getCustomer(UUID customerId) {
        Customer customer = validateExist(customerRepository.findById(customerId));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(UUID customerId, CustomerUpdateRequest request) {
        Customer customer = validateExist(customerRepository.findById(customerId));
        customer.update(request.nickname());

        return CustomerResponse.from(customerRepository.update(customer));
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.delete(customerId);
    }

    private void validateDuplicate(Optional<Customer> customer) {
        customer.ifPresent(c -> {
            throw new ConflictException(EXISTED_NICKNAME);
        });
    }

    private Customer validateExist(Optional<Customer> customer) {
        return customer.orElseThrow(() -> new NotFoundException(NOT_FOUND_CUSTOMER));
    }
}
