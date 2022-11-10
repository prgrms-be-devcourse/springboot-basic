package com.program.voucher.service;

import com.program.voucher.model.Voucher;
import com.program.voucher.model.VoucherType;
import com.program.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherProduction voucherProduction;

    public VoucherService(VoucherRepository voucherRepository, VoucherProduction voucherProduction) {
        this.voucherRepository = voucherRepository;
        this.voucherProduction = voucherProduction;
    }

    public Voucher createVoucher(String voucherTypeNumber, UUID voucherId, int discount) {
        Voucher newVoucher = voucherProduction.createVoucher(voucherTypeNumber,voucherId,discount);
        return voucherRepository.insertVoucher(newVoucher);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }
}
