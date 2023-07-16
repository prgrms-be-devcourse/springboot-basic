package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void updateCustomerIdOfVoucher(UUID voucherId, UUID customerId) {
        walletRepository.updateVoucher(voucherId, customerId);
    }

    public void updateCustomerIdNullOfVoucher(UUID voucherId) {
        walletRepository.updateVoucher(voucherId);
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return walletRepository.findAllVouchersByCustomerId(customerId);
    }

    public Customer findCustomerByVoucherId(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public List<Customer> findCustomersByVoucherType(VoucherType voucherType) {
        return walletRepository.findAllCustomersByVoucherType(voucherType);
    }

}
