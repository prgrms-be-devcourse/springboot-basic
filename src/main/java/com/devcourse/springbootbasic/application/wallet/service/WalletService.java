package com.devcourse.springbootbasic.application.wallet.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.devcourse.springbootbasic.application.wallet.repository.WalletRepository;
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
