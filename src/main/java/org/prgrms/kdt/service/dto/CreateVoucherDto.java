package org.prgrms.kdt.service.dto;

import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.domain.VoucherType;

public class CreateVoucherDto {

    private VoucherType voucherType;
    private double discountAmount;

    public CreateVoucherDto(CreateVoucherRequest createVoucherRequest) {
        this.voucherType = createVoucherRequest.getVoucherType();
        this.discountAmount = createVoucherRequest.getDiscountAmount();
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
