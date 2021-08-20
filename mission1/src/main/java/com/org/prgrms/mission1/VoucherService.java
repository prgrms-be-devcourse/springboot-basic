package com.org.prgrms.mission1;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    private static final long DEFAULT_PERCENT = 10L;

    private static final long DAFAULT_AMOUNT = 500l;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public void createVoucher(VoucherType voucherType) {  //Voucher 객체 생성
        Voucher voucher;
        if(voucherType == voucherType.FIXED_AMOUNT)
            voucher = new FixedAmoutVoucher(UUID.randomUUID(), DAFAULT_AMOUNT);
        else if (voucherType == voucherType.PERCENT_DISCOUNT)
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), DEFAULT_PERCENT);
        else
            voucher = new FixedAmoutVoucher(UUID.randomUUID(), DAFAULT_AMOUNT);


        voucherRepository.insert(voucher);

    }
    public List<Voucher> getAllVoucher(){
        return voucherRepository.findAll();
    }

    }