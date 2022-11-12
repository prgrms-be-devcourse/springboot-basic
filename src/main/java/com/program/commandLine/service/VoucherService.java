package com.program.commandLine.service;

import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher createVoucher(String voucherTypeNumber, UUID voucherId, int discount) {
        Voucher newVoucher = voucherFactory.createVoucher(voucherTypeNumber,voucherId,discount);
        return voucherRepository.insertVoucher(newVoucher);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
