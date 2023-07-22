package org.prgrms.application.domain.voucher;

import org.prgrms.application.domain.voucher.typepolicy.VoucherTypePolicy;
import org.prgrms.application.entity.VoucherEntity;

public class Voucher {
    private final Long voucherId;
    private final VoucherTypePolicy voucherTypePolicy;

    public Voucher(Long voucherId, VoucherTypePolicy voucherTypePolicy) {
        this.voucherId = voucherId;
        this.voucherTypePolicy = voucherTypePolicy;
    }

    public static Voucher of(Long voucherId, VoucherTypePolicy voucherTypePolicy) {
        return new Voucher(voucherId,voucherTypePolicy);
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public VoucherEntity toEntity(){
        return new VoucherEntity(this.voucherId, this.voucherTypePolicy);
    }

    public VoucherTypePolicy getVoucherTypePolicy() {
        return voucherTypePolicy;
    }
}
