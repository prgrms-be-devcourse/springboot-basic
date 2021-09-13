package org.prgms.w3d1.service;

import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.repository.VoucherWalletRepository;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherWalletRepository voucherWalletRepository;

    public CustomerService(CustomerRepository customerRepository, VoucherRepository voucherRepository, VoucherWalletRepository voucherWalletRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public Customer createCustomer(String name, String email) {
        var customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return saveCustomer(customer);
    }

    public Optional<Customer> getCustomer(UUID customerId){
        return customerRepository.findById(customerId);
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.insert(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public List<Customer> findByVoucherType(VoucherType voucherType) {
        var customerList = new ArrayList<Customer>();

        var voucherList = voucherRepository.findByVoucherType(voucherType);

        voucherList.stream()
            .filter(voucher -> voucher.getVoucherWalletId() != null)
            .map(voucher -> voucherWalletRepository.findById(voucher.getVoucherWalletId()).get())
            .map(voucherWallet -> customerRepository.findById(voucherWallet.getCustomerId())).distinct()
            .forEach(customer -> customer.ifPresent(customerList::add));

        return customerList;
    }

    public Customer updateWithNameAndEmail(UUID customerId, String name, String email) {
        return customerRepository.updateWithNameAndEmail(customerId, name, email);
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
