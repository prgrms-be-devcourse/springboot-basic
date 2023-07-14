package org.weekly.weekly.voucher.dto.request;

import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherCreationRequest {
    private final VoucherInfoRequest voucherInfoRequest;
    private final DiscountType discountType;

    public VoucherCreationRequest(VoucherInfoRequest voucherInfoRequest, DiscountType discountType) {
        this.voucherInfoRequest = voucherInfoRequest;
        this.discountType = discountType;
    }

    public Voucher toVoucher()  {
        UUID id = UUID.randomUUID();
        long amount = voucherInfoRequest.getAmount();
        LocalDate now = LocalDate.now();
        return Voucher.of(id, amount, now, voucherInfoRequest.getExpiration(), discountType);
    }
}
