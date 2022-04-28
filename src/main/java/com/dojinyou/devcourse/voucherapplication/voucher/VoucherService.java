package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherList;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private VoucherRepository voucherRepository;

    public VoucherService(MemoryVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        return voucherRepository.create(voucher);
    }

    public VoucherList findAll() {
        return voucherRepository.findAll();
    }
}
