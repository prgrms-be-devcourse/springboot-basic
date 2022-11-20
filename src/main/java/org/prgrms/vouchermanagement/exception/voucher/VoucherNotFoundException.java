package org.prgrms.vouchermanagement.exception.voucher;

public class VoucherNotFoundException extends RuntimeException {
    public VoucherNotFoundException() {
        super("존재하지 않는 Voucher입니다.");
    }
}
