package team.marco.voucher_management_system.controller.voucher.dto;

import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;

public class VoucherCreateRequest {
    private VoucherType voucherType;
    private int discountValue;

    public VoucherCreateRequest(VoucherType voucherType, int discountValue) {
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
}
