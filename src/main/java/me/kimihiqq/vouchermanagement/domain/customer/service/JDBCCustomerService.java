package me.kimihiqq.vouchermanagement.domain.customer.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.service.VoucherWalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("db")
@Slf4j
@RequiredArgsConstructor
@Service
public class JDBCCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;
    private final VoucherWalletService voucherWalletService;


    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = customerDto.toCustomer();
        return customerRepository.save(customer);
    }


    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void addVoucherToCustomer(UUID customerId, UUID voucherId) {
        voucherWalletService.addVoucherToWallet(customerId, voucherId);
        log.info("addVoucherToCustomer method executed for customer with ID: " + customerId);
    }


    @Override
    public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
        voucherWalletService.removeVoucherFromWallet(customerId, voucherId);
        log.info("removeVoucherFromCustomer method executed for customer with ID: " + customerId);
    }

    @Override
    public void updateCustomerStatus(Customer customer) {
        customerRepository.update(customer);
    }
}