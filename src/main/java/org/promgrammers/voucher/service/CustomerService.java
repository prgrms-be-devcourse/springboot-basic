package org.promgrammers.voucher.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.promgrammers.voucher.domain.dto.customer.CustomerRequestDto;
import org.promgrammers.voucher.domain.dto.customer.CustomerResponseDto;
import org.promgrammers.voucher.repository.CustomerRepository;
import org.promgrammers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequest) {
        duplicatedUsername(customerRequest.username());

        Customer customer = new Customer(UUID.randomUUID(), customerRequest.username());
        customerRepository.save(customer);
        return new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_VOUCHER));

        Customer customer = customerRepository.findByVoucherId(voucher.getVoucherId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }


}
