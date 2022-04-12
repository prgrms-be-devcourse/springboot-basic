package com.programmers.part1.order.voucher;

import com.programmers.part1.error.voucher.VoucherListEmptyException;
import com.programmers.part1.error.voucher.VoucherTypeMissingException;
import com.programmers.part1.order.voucher.entity.FixedAmountVoucher;
import com.programmers.part1.order.voucher.entity.PercentAmountVoucher;
import com.programmers.part1.order.voucher.entity.Voucher;
import com.programmers.part1.order.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * UI 계층에서 입력 받았을때 서비스 계층으로 넘겨주거나
 * 에러를 처리하는 게층입니다.
 * **/
@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void create(VoucherType voucherType, long amount){

        Voucher voucher;
        if(voucherType == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
            voucherService.saveVoucher(voucher);
        }
        else if(voucherType == VoucherType.PERCENT) {
            voucher = new PercentAmountVoucher(UUID.randomUUID(), amount);
            voucherService.saveVoucher(voucher);
        }
        else{
            throw new VoucherTypeMissingException("Voucher Type이 잘못 입력 되었습니다\n");
        }
    }

    public List<Voucher> list(){
        List<Voucher> vouchers = voucherService.getAllVoucher();
        if(vouchers.isEmpty())
            throw new VoucherListEmptyException("바우처가 존재 하지 않습니다.\n");
        return vouchers;
    }
}
