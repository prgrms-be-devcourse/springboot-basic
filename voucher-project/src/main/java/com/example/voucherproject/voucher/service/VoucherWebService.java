package com.example.voucherproject.voucher.service;

import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherFactory;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoucherWebService implements VoucherService{
    private final VoucherRepository voucherRepository;
    private final WalletRepository walletRepository;
    @Override
    @Transactional
    public Voucher createVoucher(String type, Long amount) {
        var voucher = VoucherFactory.createVoucher(VoucherType.valueOf(type), amount);
        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        var vouchers = voucherRepository.findAll();
        vouchers.sort(Comparator.comparing(Voucher::getCreatedAt));
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return voucherRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        try{
            voucherRepository.deleteById(id);

            var wallets = walletRepository.findAllByUserId(id);
            wallets.forEach(wallet-> walletRepository.deleteById(wallet.getId()));
        }catch (Exception e){
            return false;
        }
        return true;

    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, String from, String to) {
        return voucherRepository.findByTypeAndDate(type, from, to);
    }
}
