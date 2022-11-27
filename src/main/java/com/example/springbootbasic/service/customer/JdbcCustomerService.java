package com.example.springbootbasic.service.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.repository.customer.JdbcCustomerRepository;
import com.example.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.springbootbasic.exception.customer.CustomerServiceExceptionMessage.CUSTOMER_NULL_EXCEPTION;

@Service
public class JdbcCustomerService {
    private final JdbcCustomerRepository customerRepository;
    private final JdbcVoucherRepository voucherRepository;

    public JdbcCustomerService(JdbcCustomerRepository customerRepository, JdbcVoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    public List<Customer> findCustomersByStatus(CustomerStatus status) {
        return customerRepository.findCustomersByStatus(status);
    }

    public List<Customer> findCustomersWhoHasSelectedVoucher(Long voucherId) {
        Voucher findVoucher = voucherRepository.findById(voucherId);
        List<Long> customerIds = customerRepository.findCustomerIdsByVoucherId(voucherId);
        List<Customer> findCustomers = customerIds.stream()
                .map(customerRepository::findCustomerById)
                .collect(Collectors.toList());
        findCustomers.forEach(customer -> customer.receiveFrom(findVoucher));
        return findCustomers;
    }

    public Customer saveCustomer(Customer customer) {
        validateCustomerNull(customer);
        return customerRepository.saveCustomer(customer);
    }

    private void validateCustomerNull(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException(CUSTOMER_NULL_EXCEPTION.getMessage());
        }
    }

    public Customer saveVoucher(Customer customer, Voucher voucher) {
        return customerRepository.saveVoucher(customer, voucher);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findCustomerById(customerId);
    }

    public List<Voucher> findVouchersByCustomerId(long customerId) {
        List<Long> findVoucherIds = customerRepository.findVoucherIdsByCustomerId(customerId);
        return findVoucherIds.stream()
                .map(voucherRepository::findById)
                .collect(Collectors.toList());
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAllCustomers();
    }

    public void deleteAllCustomerVoucher() {
        customerRepository.deleteAllCustomerVoucher();
    }

    public void deleteAllVouchersByCustomerId(long customerId) {
        customerRepository.deleteAllVouchersByCustomerId(customerId);
    }
}
