package com.prgrms.model.voucher;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherPolicy {
    FixedAmountVoucher("1","고정 금액 할인에 따른 바우처", "얼만큼 할인 받고 싶은지 입력하세요 (단위: 원):"),
    PercentDiscountVoucher("2","할인율에 따른 바우처", "할인율을 입력하세요 (0~100 사이의 값):");

    private final String number;
    private final String policy;
    private final String discountGuide;

    VoucherPolicy(String number,String policy, String discountGuide) {
        this.number = number;
        this.policy = policy;
        this.discountGuide = discountGuide;
    }

    public static Optional<VoucherPolicy> findBySelectedPolicy(String selectedPolicy){
        return Arrays.stream(VoucherPolicy.values())
                .filter(p -> p.number.equals(selectedPolicy))
                .findFirst();
    }

    @Override
    public String toString() {
        return number +"번 : "+policy;
    }

    public String getDiscountGuide(){
        return discountGuide;
    }
}

