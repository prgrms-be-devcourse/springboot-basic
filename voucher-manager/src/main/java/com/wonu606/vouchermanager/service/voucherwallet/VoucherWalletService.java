package com.wonu606.vouchermanager.service.voucherwallet;

import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.repository.voucherwallet.VoucherWalletRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherWalletService {

    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(
            VoucherWalletRepository voucherWalletRepository) {
        this.voucherWalletRepository = voucherWalletRepository;
    }

    public List<UUID> findOwnedVouchersByCustomer(Email email) {
        return voucherWalletRepository.findIdByCustomerEmailAddress(email);
    }

    public void deleteByWallet(VoucherWalletDto voucherWalletDto) {
        VoucherWallet voucherWallet = voucherWalletDto.toEntity();
        voucherWalletRepository.deleteByWallet(voucherWallet);
    }

    public VoucherWallet save(VoucherWalletDto voucherWalletDto) {
        VoucherWallet voucherWallet = voucherWalletDto.toEntity();
        return voucherWalletRepository.save(voucherWallet);
    }

    public List<String> findEmailAddressesByVoucherId(UUID voucherId) {
        return voucherWalletRepository.findEmailAddressesByVoucherId(voucherId);
    }
}
