package org.prgrms.kdt.voucher;

public class HowMuchDiscount {
    String voucherType;
    long amount;
    float percent;

    public static void howMuchDiscountMessage(final String voucherType) {
        if (voucherType.equals("FixedAmountVoucher")) {
            System.out.println("할인 가격을 얼마로 설정하시겠습니까?");
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            System.out.println("할인율을 몇 퍼센트로 설정하시겠습니까?");
        } else {
            System.out.println("=== Input type error ===");
            System.out.println(voucherType + "은(는) 존재하지 않는 voucher type 입니다.");
            System.out.println("'FixedAmountVoucher', 'PercentDiscountVoucher'를 입력하세요.");
        }
    }
}
