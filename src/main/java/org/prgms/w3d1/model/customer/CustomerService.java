package org.prgms.w3d1.model.customer;

import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.model.wallet.VoucherWalletRepository;
import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;

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

    public Customer getCustomer(UUID customerId){
        return customerRepository
            .findById(customerId)
            .orElseThrow(()-> new RuntimeException("Can not find a customer for " + customerId));
    }

    public void saveCustomer(Customer customer){
        customerRepository.insert(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public List<Customer> findByVoucherType(VoucherType voucherType) {
        return voucherRepository.findByVoucherType(voucherType).stream()
            .filter(voucher -> voucher.getVoucherWalletId() != null)
            .map(voucher -> voucherWalletRepository.findById(voucher.getVoucherWalletId()).get())
            .map(wallet -> wallet.getCustomerId()).filter(Objects::nonNull).distinct()
            .map(customerId -> customerRepository.findById(customerId).get()).collect(Collectors.toList());
    }
}
