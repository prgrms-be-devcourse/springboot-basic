package com.programmers.part1.order.voucher;

import com.programmers.part1.exception.voucher.VoucherListEmptyException;
import com.programmers.part1.exception.voucher.VoucherTypeMissingException;
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

    public void create(VoucherType voucherType, long amount) throws VoucherTypeMissingException{
        switch (voucherType){
            case FIXED -> voucherService.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
            case PERCENT -> voucherService.saveVoucher(new PercentAmountVoucher(UUID.randomUUID(), (int)amount));
        }
    }

    public List<Voucher> list() throws VoucherListEmptyException{
        List<Voucher> vouchers = voucherService.getAllVoucher();
        if(vouchers.isEmpty())
            throw new VoucherListEmptyException("바우처가 존재 하지 않습니다.\n");
        return vouchers;
    }
}
