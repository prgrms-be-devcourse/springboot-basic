package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.domain.VoucherType;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class VoucherFactoryTest {

    Pattern pattern = Pattern.compile("\\w+");

    @Test
    void testFindType() {
        String str1 = "FixedAmountVoucher{id=df067d67-faef-4669-aed0-3b2d22d886a5, amount=100}.txt";

        String group = "";
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find()) {
            group = matcher.group();
        }
        assertThat(group).isEqualTo("FixedAmountVoucher");
    }

    @Test
    void testSupport() {

        RateAmountVoucher rateAmountVoucher = new RateAmountVoucher(UUID.randomUUID());
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID());
        List<Voucher> vouchers = Arrays.asList(rateAmountVoucher,
            fixedAmountVoucher);

        VoucherType type = VoucherType.getType("FixedAmountVoucher");

        Voucher supportVoucher = vouchers.stream()
            .filter(voucher -> voucher.support(type))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

        assertThat(fixedAmountVoucher).isEqualTo(supportVoucher);
    }
}
