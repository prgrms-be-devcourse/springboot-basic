package org.programmers.voucher.service;

import org.programmers.voucher.domain.FixedAmountVoucher;
import org.programmers.voucher.domain.PercentDiscountVoucher;
import org.programmers.voucher.domain.Voucher;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    private long amount;
    private long percent;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> listVoucher(){
        return voucherRepository.findAll();
    }

    //todo
    public Voucher makeVoucher(VoucherType voucherType){

        return null;
    }
}
