package org.prgrms.application.entity;


import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.typepolicy.VoucherTypePolicy;

public class VoucherEntity {
    private Long voucherId;
    private VoucherTypePolicy voucherTypePolicy;

    public VoucherEntity(Long voucherId, VoucherTypePolicy voucherTypePolicy) {
        this.voucherId = voucherId;
        this.voucherTypePolicy = voucherTypePolicy;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Voucher toDomain(){ // 수정 변환해주는 것을 독립,
        return new Voucher(this.voucherId, this.voucherTypePolicy);
    }
}
