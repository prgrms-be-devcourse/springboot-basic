package org.promgrammers.springbootbasic.domain.voucher.dto.request;

import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;

public record CreateVoucherRequest(@NotNull(message = "바우처 타입을 입력해주세요.") VoucherType voucherType,
                                   @NotNull(message = "할인 금액을 입력해주세요.") long discountAmount) {

    public static CreateVoucherRequest of(VoucherType voucherType, long discountAmount) {
        return new CreateVoucherRequest(voucherType, discountAmount);
    }

}
