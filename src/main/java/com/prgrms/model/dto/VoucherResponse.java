package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;

import lombok.*;

import java.util.Objects;


@Getter
@Builder
@AllArgsConstructor
public class VoucherResponse {
    private final String exceptionMessage = "기본생성자는 만들 수 없습니다.";
    private VoucherType voucherType;
    private Discount discount;

    public VoucherResponse() {
        throw new IllegalArgumentException(exceptionMessage);
    }

    public double getDiscountedValue() {
        return discount.getDiscount();
    }

    public static VoucherResponse of(Voucher voucher) {
        return VoucherResponse.builder()
                .voucherType(voucher.getVoucherPolicy())
                .discount(voucher.getVoucherDiscount())
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherType, discount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VoucherResponse other = (VoucherResponse) obj;
        return Objects.equals(voucherType, other.voucherType) &&
                Objects.equals(discount, other.discount);
    }
}
