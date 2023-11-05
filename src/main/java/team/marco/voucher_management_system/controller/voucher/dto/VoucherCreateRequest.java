package team.marco.voucher_management_system.controller.voucher.dto;

import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;

public class VoucherCreateRequest {
    private VoucherType voucherType;
    private int discountValue;

    public VoucherCreateRequest(VoucherType voucherType, int discountValue) {
        validateDiscountValue(discountValue);

        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherCreateServiceRequest toServiceRequest() {
        return new VoucherCreateServiceRequest(
                voucherType,
                discountValue);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    private static void validateDiscountValue(int discountValue) {
        if (isNotPositive(discountValue)) throw new IllegalArgumentException("할인 금액 또는 할인율은 양수입니다.");
    }

    private static boolean isNotPositive(int discountValue) {
        return discountValue <= 0;
    }
}
