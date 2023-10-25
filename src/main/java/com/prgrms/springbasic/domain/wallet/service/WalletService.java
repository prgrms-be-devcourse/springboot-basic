package com.prgrms.springbasic.domain.wallet.service;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.customer.repository.CustomerRepository;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import com.prgrms.springbasic.domain.wallet.dto.CreateWalletRequest;
import com.prgrms.springbasic.domain.wallet.dto.WalletResponse;
import com.prgrms.springbasic.domain.wallet.entity.Wallet;
import com.prgrms.springbasic.domain.wallet.repository.JdbcWalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;
    private final JdbcWalletRepository jdbcWalletRepository;

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository, JdbcWalletRepository jdbcWalletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.jdbcWalletRepository = jdbcWalletRepository;
    }

    public WalletResponse saveWallet(CreateWalletRequest request) {
        Customer customer = findCustomer(request.customer_id());
        Voucher voucher = findVoucher(request.voucher_id());
        Wallet wallet = jdbcWalletRepository.saveWallet(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
        return WalletResponse.from(wallet);
    }

    public List<VoucherResponse> getVouchersByCustomerId(UUID customerId) {
        List<Wallet> wallets = jdbcWalletRepository.findWalletsByCustomerId(findCustomer(customerId).getCustomerId());

        return wallets.stream()
                .map(wallet -> findVoucher(wallet.getVoucher_id()))
                .map(VoucherResponse::from)
                .toList();
    }

    private Customer findCustomer(UUID customerId) {
        return customerRepository.findCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    private Voucher findVoucher(UUID voucherId) {
        return voucherRepository.findVoucherById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
    }
}
