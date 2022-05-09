package org.programmers.springbootbasic.voucher.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum VoucherType {

    FIXED("정량 할인 바우처", "할인액", "정해진 만큼의 금액을 깎아주는 바우처입니다."),
    RATE("비율 할인 바우처", "할인율", "정해진 비율만큼의 금액을 깎아주는 바우처입니다.");

    private final String name;
    private final String discountUnitMessage;
    private final int ordinal;
    private final String description;

    VoucherType(String name, String discountUnitName, String description) {
        this.name = name;
        this.discountUnitMessage = discountUnitName;
        this.ordinal = this.ordinal();
        this.description = description;
    }

    public static VoucherType findTypeByOrdinal(int ordinal) throws IllegalArgumentException {
        ordinal -= 1;     //ordinal 0부터 시작하기 때문
        for (var voucherType : VoucherType.values()) {
            if (voucherType.getOrdinal() == ordinal) {
                return voucherType;
            }
        }
        throw new IllegalArgumentException(
                "Illegal ordinal value. No corresponding voucherType found. ordinal=" + ordinal);
    }

    public static String dataOf(Voucher voucher) throws IllegalStateException {
        var stringBuilder = new StringBuilder();
        var voucherTypes = VoucherType.values();
        for (VoucherType voucherType : voucherTypes) {
            if (voucher.getType() == voucherType) {
                stringBuilder.append("바우처 일련번호: ");
                stringBuilder.append(voucher.getId());
                stringBuilder.append("\n    ");
                stringBuilder.append("종류: ");
                stringBuilder.append(voucherType.getName());
                stringBuilder.append("\n    ");
                stringBuilder.append(voucherType.getDiscountUnitMessage());
                stringBuilder.append(": ");
                stringBuilder.append(voucher.getAmount());
                stringBuilder.append("\n    ");
                return stringBuilder.toString();
            }
        }
        stringBuilder.append("바우처 일련번호: ");
        stringBuilder.append(voucher.getId());
        stringBuilder.append("\n    ");
        stringBuilder.append("해당 종류 바우처가 정의되어 있지 않습니다: 유효하지 않은 바우처");
        return stringBuilder.toString();
    }

    public String explainThisType() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(this.getName());
        stringBuilder.append("\n    ");
        stringBuilder.append(this.getDescription());
        stringBuilder.append("\n    ");

        return stringBuilder.toString();
    }
}