package team.marco.voucher_management_system.controller.voucher;

import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;

import java.util.Optional;

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
                discountValue,
                Optional.empty(),
                Optional.empty()
        );
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }
}
