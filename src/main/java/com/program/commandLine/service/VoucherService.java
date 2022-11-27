package com.program.commandLine.service;

import com.program.commandLine.model.VoucherInputData;
import com.program.commandLine.model.VoucherWallet;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherFactory;
import com.program.commandLine.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(VoucherInputData inputData) {
        Voucher newVoucher = voucherFactory.createVoucher(inputData.getVoucherType(), inputData.getVoucherId(), inputData.getDiscount());
        return voucherRepository.insert(newVoucher);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public List<Voucher> getVouchersByWallet(List<VoucherWallet> wallets) {
        List<Voucher> vouchers = new ArrayList<>();
        wallets.forEach(wallet -> {
            Optional<Voucher> findVoucher = voucherRepository.findById(wallet.voucherId());
            findVoucher.ifPresent(vouchers::add);
        });
        return vouchers;
    }

    public Optional<Voucher> getVoucherById(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public void deleteVoucher(UUID voucherId){
        voucherRepository.deleteById(voucherId);
    }


}
