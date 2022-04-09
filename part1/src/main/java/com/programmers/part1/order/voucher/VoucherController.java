package com.programmers.part1.order.voucher;

import com.programmers.part1.order.voucher.entity.FixedAmountVoucher;
import com.programmers.part1.order.voucher.entity.PercentAmountVoucher;
import com.programmers.part1.order.voucher.entity.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void create(int voucherType, long amount){

        //TODO : voucherType이 1, 2가 아니면 에러
        Voucher voucher;
        if(voucherType == 1)
            voucher = new FixedAmountVoucher(UUID.randomUUID(),amount);
        else if(voucherType == 2){
            //TODO : amount가 100을 넘길 경우 에러
            voucher = new PercentAmountVoucher(UUID.randomUUID(),amount);
        }

    }

    public List<Voucher> list(){
        //TODO : voucher가 없을 경우 에러
        return voucherService.getAllVoucher();
    }
}
