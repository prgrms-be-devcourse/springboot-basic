package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasicCustomerService implements CustomerVoucherService {

    private final CustomerRepository customerRepository;
    private final VoucherRepository jdbcVoucherRepository;

    public BasicCustomerService(CustomerRepository customerRepository, VoucherRepository jdbcVoucherRepository) {
        this.customerRepository = customerRepository;
        this.jdbcVoucherRepository = jdbcVoucherRepository;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByVoucher(long voucherId) {
        return customerRepository.findByVoucher(voucherId);
    }

    @Override
    public List<Voucher> findAllVoucherByCustomer(long customerId) {
        return jdbcVoucherRepository.findAllByCustomer(customerId);
    }

    @Override
    public void deleteVoucherFromWallet(long customerId, long voucherId) {
        jdbcVoucherRepository.findById(voucherId).ifPresent(
                voucher -> {
                    if (voucher.getCustomerId() == customerId) {
                        jdbcVoucherRepository.deleteById(voucherId);
                    }
                }
        );
    }

    @Override
    public Customer create(String username, String alias) {
        Customer customer = new Customer(username, alias);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void openStorage() {
        customerRepository.loadCustomers();
    }

    @Override
    public void closeStorage() {
        customerRepository.persistCustomers();
    }

    @Override
    public List<Customer> listAll() {
        return customerRepository.listAll();
    }

}
