package org.prgrms.application.domain.voucher;

import org.prgrms.application.domain.voucher.typepolicy.VoucherTypePolicy;

public record VoucherDto(Long voucherId, VoucherTypePolicy voucherTypePolicy) {

    public static VoucherDto of(Voucher voucher){
        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherTypePolicy());
    }
}
