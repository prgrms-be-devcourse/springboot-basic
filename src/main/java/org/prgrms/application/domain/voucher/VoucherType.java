package org.prgrms.application.domain.voucher;

import org.prgrms.application.domain.voucher.typePolicy.FixedTypePolicy;
import org.prgrms.application.domain.voucher.typePolicy.PercentTypePolicy;
import org.prgrms.application.domain.voucher.typePolicy.VoucherTypePolicy;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
    FIXED("fixed", FixedTypePolicy::new),
    PERCENT("percent", PercentTypePolicy::new);

    private final String typeName;
    private final Function<Double,VoucherTypePolicy> typePolicyFactory;

    VoucherType(String typeName,Function<Double,VoucherTypePolicy> typePolicyFactory) {
        this.typeName = typeName;
        this.typePolicyFactory = typePolicyFactory;
    }

    public static VoucherType findBySelection(String selection) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(selection))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 형식 입력입니다."));
    }

}
