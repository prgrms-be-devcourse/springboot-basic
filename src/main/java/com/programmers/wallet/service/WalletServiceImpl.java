package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.customer.service.CustomerService;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerService customerService;

    public WalletServiceImpl(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository, CustomerService customerService) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Customer assignVoucher(UUID customerId, UUID voucherId) {
//        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);
        Customer customer = customerService.findById(customerId);
//        Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Voucher voucher = voucherOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));

        walletRepository.assignVoucher(customer, voucher);
        customer.addVoucher(voucher);

        return customer;
    }

    @Override
    public List<Voucher> searchVouchersByCustomerId(UUID customerId) {
        return walletRepository.findVouchersByCustomerId(customerId);
    }

    @Override
    public Customer searchCustomerByVoucherId(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId)
                .orElseThrow(() -> new RuntimeException("조회 에러"));
    }

    @Override
    @Transactional
    public void removeCustomerVoucher(UUID customerId, UUID voucherId) {
//        customerRepository.findAllById(customerId);
//        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucherId);
        Customer findCustomer = customerService
                .findById(customerId);
//        Customer findCustomer = customerOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Voucher findVoucher = voucherOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));

        findCustomer.removeVoucher(findVoucher);

        walletRepository.deleteCustomerVoucher(customerId, voucherId);
    }
}
