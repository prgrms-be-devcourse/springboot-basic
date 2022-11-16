package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
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

    public WalletServiceImpl(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    @Transactional
    public Customer assignVoucher(Customer customer, Voucher voucher) {
        Optional<Customer> customerOptional = customerRepository.findById(customer.getCustomerId());
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucher.getVoucherId());

        customerOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        voucherOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));

        customer.addVoucher(voucher);
        return walletRepository.assignVoucher(customer, voucher);
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
    public void removeCustomerVoucher(Customer customer, Voucher voucher) {
        Optional<Customer> customerOptional = customerRepository.findById(customer.getCustomerId());
        Optional<Voucher> voucherOptional = voucherRepository.findById(voucher.getVoucherId());

        Customer findCustomer = customerOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Voucher findVoucher = voucherOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 바우처입니다."));

        customer.removeVoucher(findVoucher);

        walletRepository.deleteCustomerVoucher(customer.getCustomerId(), voucher.getVoucherId());
    }
}
