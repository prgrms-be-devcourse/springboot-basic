package com.prgrms.voucher_manager;

import com.prgrms.voucher_manager.voucher.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherManager {

    private final VoucherService voucherService;

    public VoucherManager(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    void createVoucher(String voucherType,long value) {
        if (voucherType.equals("1")) {
            voucherService.createFixedAmountVoucher(value);
        } else if (voucherType.equals("2")) {
            voucherService.createPercentDiscountVoucher(value);
        }
    }

    void checkType(String voucherType){
        if(!voucherType.equals("1") && !voucherType.equals("2")) throw new IllegalArgumentException();
    }

    public void getFindAllVoucher() {
        voucherService.getFindAllVoucher();
    }
}
