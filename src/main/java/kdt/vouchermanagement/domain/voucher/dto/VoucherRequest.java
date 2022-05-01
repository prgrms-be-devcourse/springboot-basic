package kdt.vouchermanagement.domain.voucher.dto;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

public class VoucherRequest {

    private VoucherType voucherType;
    private int discountValue;

    public VoucherRequest() {
    }

    public VoucherRequest(VoucherType voucherType, int discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public Voucher toDomain() {
        validateVoucherType();
        Voucher voucher = voucherType.create(discountValue);
        return voucher;
    }

    private void validateVoucherType() {
        if (this.voucherType.equals(VoucherType.NONE)) {
            throw new IllegalArgumentException("입력한 VoucherType 값이 유효하지 않습니다.");
        }
    }
}
