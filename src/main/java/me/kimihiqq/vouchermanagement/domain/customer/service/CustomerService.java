package me.kimihiqq.vouchermanagement.domain.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.service.VoucherWalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Profile({"db", "test"})
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VoucherWalletService voucherWalletService;


    public Customer createCustomer(CustomerDto customerDto) {
        UUID id = UUID.randomUUID();
        Customer customer = customerDto.toCustomer(id);
        return customerRepository.save(customer);
    }


    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public void updateCustomerStatus(Customer customer) {
        customerRepository.update(customer);
    }


    public List<Customer> findCustomersWithVoucher(UUID voucherId) {
        Set<UUID> customerIds = voucherWalletService.findCustomerIdsByVoucherId(voucherId);
        return customerIds.stream()
                .map(this::findCustomerById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


}
