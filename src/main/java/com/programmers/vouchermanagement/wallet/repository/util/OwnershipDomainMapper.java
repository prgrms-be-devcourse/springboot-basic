package com.programmers.vouchermanagement.wallet.repository.util;

import com.programmers.vouchermanagement.util.DomainMapper;
import com.programmers.vouchermanagement.wallet.domain.Ownership;

import java.util.HashMap;
import java.util.Map;

public class OwnershipDomainMapper extends DomainMapper {
    public static final String VOUCHER_ID_KEY = "voucher_id";
    public static final String CUSTOMER_ID_KEY = "customer_id";

    private OwnershipDomainMapper() {
    }

    public static Map<String, Object> ownershipToParamMap(Ownership ownership) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(VOUCHER_ID_KEY, ownership.voucherId().toString().getBytes());
        paramMap.put(CUSTOMER_ID_KEY, ownership.customerId().toString().getBytes());
        return paramMap;
    }
}
