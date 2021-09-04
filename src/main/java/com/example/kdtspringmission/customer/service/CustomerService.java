package com.example.kdtspringmission.customer.service;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.repository.CustomerRepository;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VoucherService voucherService;


    public CustomerService(CustomerRepository customerRepository, VoucherService voucherService) {
        this.customerRepository = customerRepository;
        this.voucherService = voucherService;
    }


    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    public void assignVoucher(String customerName, UUID voucherId) {
        Customer customer = customerRepository.findByName(customerName).orElseThrow(() -> new IllegalArgumentException("No matched name"));
        Voucher voucher = voucherService.getVoucher(voucherId);

        customer.addVoucher(voucher);
        voucherService.update(voucher);
    }

    public void deleteVoucher(String customerName, UUID voucherId) {
        Customer customer = customerRepository.findByName(customerName).orElseThrow(() -> new IllegalArgumentException("No matched name"));
        Voucher voucher = voucherService.getVoucher(voucherId);

        customer.deleteVoucher(voucher);
        voucherService.update(voucher);
    }

    public Customer getCustomerOwn(Voucher voucher) {
        return customerRepository.findById(voucher.getOwnerId()).orElseThrow(() -> new IllegalArgumentException("No matched ID"));
    }

    public List<Voucher> getWallet(String customerName) {
        Customer customer = customerRepository.findByName(customerName).orElseThrow(() -> new IllegalArgumentException("No matched name"));
        return voucherService.findVouchersByOwnerId(customer.getCustomerId());
    }
}
