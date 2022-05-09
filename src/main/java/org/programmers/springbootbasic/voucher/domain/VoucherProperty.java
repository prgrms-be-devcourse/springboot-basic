package org.programmers.springbootbasic.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "voucher")
@RequiredArgsConstructor
@Getter
@Setter
public class VoucherProperty {

    private FixedDiscountVoucherProperty fixed;
    private RateDiscountVoucherProperty rate;

    public record FixedDiscountVoucherProperty(int maximumAmount, int minimumAmount) {
    }

    public record RateDiscountVoucherProperty(int maximumAmount, int minimumAmount) {
    }
}
