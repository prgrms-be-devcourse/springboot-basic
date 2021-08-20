package org.programmers.applicationcontext;

import java.util.UUID;

public class VoucherService { // 바우쳐에 관련된 기능을 정의하는 클래스, 예를 들면 바우쳐 생성, 조회 등
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        //orElseThrow()는 오류 발생(RuntimeException) 시 어떤 문장을 출력할지 결정한다
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can't find a voucher for "+ voucherId));

    }

    public void useVoucher(Voucher voucher) {
    }

    public FixedAmountVoucher createFixedAmountVoucher(UUID voucherId, long amount){ // 새로 생긴 기능1
        return new FixedAmountVoucher(voucherId, amount);
    }

    public PercentDiscountVoucher createPercentDiscountVoucher(UUID voucherId, long percent){ // 새로 생긴 기능2
        return new PercentDiscountVoucher(voucherId, percent);
    }
}
