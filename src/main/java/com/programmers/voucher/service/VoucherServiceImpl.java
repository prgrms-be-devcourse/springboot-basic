package com.programmers.voucher.service;

import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherValidator;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.message.ErrorMessage.VOUCHER_ID_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public Voucher register(String voucherType, String value) {
        Voucher voucher = VoucherValidator.getValidateVoucher(voucherType, value);
        return voucherRepository.registerVoucher(voucher);
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        return result.orElseThrow(() ->
                new RuntimeException(VOUCHER_ID_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAllVouchers();
    }

    @Override
    public List<Voucher> searchVouchersByCustomerId(UUID customerId) {
        return walletRepository.findVouchersByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteVoucher(voucherId);
    }
}
