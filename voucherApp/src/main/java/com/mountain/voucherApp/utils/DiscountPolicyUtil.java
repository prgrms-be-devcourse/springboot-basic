package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.voucher.Voucher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DiscountPolicyUtil {

    private static Map<Integer ,DiscountPolicy> map = null;

    public static Voucher getVoucher(int policyId) {
        Map<Integer, DiscountPolicy> discountPolicyMap = getDiscountPolicyMap();
        DiscountPolicy discountPolicy = discountPolicyMap.get(policyId);
        return discountPolicy.getVoucher();
    }

    public static Map<Integer,DiscountPolicy> getDiscountPolicyMap() {
        if (map == null) {
            map = new HashMap<>();
            Arrays.stream(DiscountPolicy.values())
                    .forEach((dp) -> {
                        map.put(dp.getPolicyId(), dp);
                    });
        }
        return map;
    }
}
