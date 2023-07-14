package org.weekly.weekly.voucher.dto.request;

import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherCreationRequest {
    private VoucherInfoRequest voucherInfoRequest;
    private DiscountType discountType;

    public VoucherCreationRequest(VoucherInfoRequest voucherInfoRequest, DiscountType discountType) {
        this.voucherInfoRequest = voucherInfoRequest;
        this.discountType = discountType;
    }

    public VoucherInfoRequest getVoucherInfoRequest() {
        return voucherInfoRequest;
    }

    public void setVoucherInfoRequest(VoucherInfoRequest voucherInfoRequest) {
        this.voucherInfoRequest = voucherInfoRequest;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public Voucher toVoucher()  {
        UUID id = UUID.randomUUID();
        long amount = voucherInfoRequest.getAmount();
        LocalDate now = LocalDate.now();
        return Voucher.of(id, amount, now, voucherInfoRequest.getExpiration(), discountType);
    }
}
