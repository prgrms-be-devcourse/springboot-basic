package org.prgrms.springorder.domain.voucher_wallet.service;

import java.util.UUID;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher_wallet.model.VoucherWallet;
import org.prgrms.springorder.domain.voucher_wallet.repository.VoucherWalletRepository;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    @Transactional
    public void allocateVoucher(UUID customerId, UUID voucherId) {
        VoucherWallet voucherWallet = VoucherWallet.create(UUID.randomUUID(), customerId,
            voucherId);

        voucherWalletRepository.insert(voucherWallet);
    }

    @Transactional
    public void deleteVoucherByCustomerId(UUID voucherId, UUID customerId) {
        boolean exists = voucherWalletRepository.existsByCustomerIdAndVoucherId(customerId,
            voucherId);

        if (!exists) {
            throw new EntityNotFoundException(VoucherWallet.class,
                voucherId.toString() + ", " + customerId.toString());
        }

        voucherWalletRepository.deleteByCustomerIdAndVoucherID(customerId, voucherId);
    }

    @Transactional(readOnly = true)
    public Wallet findAllVouchers(UUID customerId) {
        return voucherWalletRepository.findAllWithCustomerAndVoucher(customerId)
            .orElseThrow(() -> new EntityNotFoundException(VoucherWallet.class, customerId));
    }

    @Transactional(readOnly = true)
    public CustomerWithVoucher findCustomerWithVoucherByVoucherId(UUID voucherId) {
        return voucherWalletRepository.findByVoucherIdWithCustomer(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(CustomerWithVoucher.class, voucherId));
    }

}
