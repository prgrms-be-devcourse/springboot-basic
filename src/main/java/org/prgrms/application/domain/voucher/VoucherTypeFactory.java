package org.prgrms.application.domain.voucher;

import org.prgrms.application.service.FixedVoucherService;
import org.prgrms.application.service.PercentVoucherService;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.Math.abs;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

@Component
public class VoucherTypeFactory {

    private final FixedVoucherService fixedVoucherService;
    private final PercentVoucherService percentVoucherService;

    public VoucherTypeFactory(FixedVoucherService fixedVoucherService, PercentVoucherService percentVoucherService) {
        this.fixedVoucherService = fixedVoucherService;
        this.percentVoucherService = percentVoucherService;
    }

    public VoucherService getType(VoucherType voucherType){
        final VoucherService voucherService;

        switch (voucherType){
            case FIXED:
                voucherService = fixedVoucherService;
                break;
            case PERCENT:
                voucherService = percentVoucherService;
                break;
            default:
                throw new IllegalArgumentException("잘못된 바우처 타입입니다.");
        }
        return  voucherService;
    }


    public Voucher create(VoucherType voucherType, double voucherDetail) {
        long randomId = abs(new Random().nextLong());

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(randomId, FIXED, voucherDetail);

            case PERCENT:
                return new PercentAmountVoucher(randomId, PERCENT, voucherDetail);

            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
