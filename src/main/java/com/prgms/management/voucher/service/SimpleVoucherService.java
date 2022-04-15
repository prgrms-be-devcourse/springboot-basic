package com.prgms.management.voucher.service;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import com.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;

    public SimpleVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() throws VoucherException {
        return voucherRepository.findAll();
    }

    public Voucher saveVoucher(Voucher voucher) throws VoucherException {
        while (true) {
            try {
                voucherRepository.findById(voucher.getVoucherId());
            } catch (VoucherException e) {
                return voucherRepository.save(voucher);
            }
            voucher.resetVoucherId();
        }
    }
}
