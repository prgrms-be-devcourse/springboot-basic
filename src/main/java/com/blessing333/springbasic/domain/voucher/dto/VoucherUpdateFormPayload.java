package com.blessing333.springbasic.domain.voucher.dto;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
@Setter
public class VoucherUpdateFormPayload {
    @NotNull
    private final String voucherId;
    @NotBlank
    private final String voucherType;
    @NotBlank
    private final String discountAmount;

    //TODO: 질문2
    public static VoucherUpdateFormPayload fromEntity(Voucher voucher){
        String id = voucher.getVoucherId().toString();
        String type = voucher.getVoucherType().getOptionNumber();
        String discountAmount = Long.toString(voucher.getDiscountAmount());
        return new VoucherUpdateFormPayload(id,type,discountAmount);
    }
}
