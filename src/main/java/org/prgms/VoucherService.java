package org.prgms;

import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }


    public Voucher  getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for" + voucherId));
                //voucherRepository 에서 voucherId를 가져오려고 하지만 없는 경우에는 에러를 처리함(cna not find a voucher for id)
    }

    public void useVoucher(Voucher voucher) {
    }
}
