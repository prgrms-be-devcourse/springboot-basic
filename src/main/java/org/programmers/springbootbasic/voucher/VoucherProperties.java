package org.programmers.springbootbasic.voucher;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "voucher")
@RequiredArgsConstructor
@Getter
@Setter(AccessLevel.PACKAGE)
public class VoucherProperties implements InitializingBean {

    private FixedDiscountVoucherProperties fixed = new FixedDiscountVoucherProperties();
    private RateDiscountVoucherProperties rate = new RateDiscountVoucherProperties();

    public int getFixedMinimumAmount() {
        return this.fixed.getMinimumAmount();
    }

    public int getFixedMaximumAmount() {
        return this.fixed.getMaximumAmount();
    }

    @Getter
    @Setter(AccessLevel.PACKAGE)
    public static class FixedDiscountVoucherProperties {
        private int minimumAmount;
        private int maximumAmount;
    }

    @Getter
    @Setter(AccessLevel.PACKAGE)
    public static class RateDiscountVoucherProperties {
        private int minimumAmount;
        private int maximumAmount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
