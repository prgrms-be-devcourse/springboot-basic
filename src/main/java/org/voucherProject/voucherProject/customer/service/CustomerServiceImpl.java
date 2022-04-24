package org.voucherProject.voucherProject.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.repository.CustomerDao;
import org.voucherProject.voucherProject.voucher.entity.VoucherType;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerRepository;

    @Override
    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public Customer findByName(String customerName) {
        return customerRepository.findByName(customerName)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public Customer findByEmail(String customerEmail) {
        return customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public List<Customer> findByVoucherType(VoucherType voucherType) {
        return customerRepository.findByVoucherType(voucherType);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
