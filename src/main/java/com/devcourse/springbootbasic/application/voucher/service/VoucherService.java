package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAllVouchers();
    }

    public Voucher findVoucherById(Voucher voucher) {
        return voucherRepository.findById(voucher.getVoucherId())
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_VOUCHER_INFO.getMessageText()));
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public Voucher deleteVoucherById(Voucher voucher) {
        var foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        voucherRepository.deleteById(voucher.getVoucherId());
        return foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_VOUCHER_INFO.getMessageText()));
    }

}