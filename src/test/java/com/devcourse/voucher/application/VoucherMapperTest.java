package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.DiscountPolicy;
import com.devcourse.voucher.domain.FixedAmountPolicy;
import com.devcourse.voucher.domain.PercentDiscountPolicy;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.devcourse.voucher.domain.VoucherType.FIXED;
import static com.devcourse.voucher.domain.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringJUnitConfig(VoucherMapper.class)
class VoucherMapperTest {
    @Autowired
    private VoucherMapper voucherMapper;
    private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

    @ParameterizedTest
    @DisplayName("매핑된 결과물이 요청에 맞는 결과물이어야 한다.")
    @MethodSource("symbolAndPolicySource")
    void mapToFixedTest(VoucherType type, Class<? extends DiscountPolicy> clazz) {
        // given
        int discount = 100;
        CreateVoucherRequest request = new CreateVoucherRequest(type, discount, expiredAt);

        // when
        Voucher voucher = voucherMapper.toEntity(request);

        // then
        assertThat(voucher.getDiscountPolicy()).isInstanceOf(clazz);
        assertThat(voucher.getExpireAt()).isEqualTo(expiredAt);
        assertThat(voucher.isUsed()).isFalse();
    }

    @Test
    @DisplayName("DTO로 변환될 때 내용물에 변화가 없어야 한다.")
    void toResponseListTest() {
        // given
        int discount = 1000;
        Voucher voucher = Voucher.percent(discount, expiredAt);

        // when
        GetVoucherResponse response = voucherMapper.toResponse(voucher);

        // then
        assertThat(response).isNotNull();
        assertThat(response.type()).isEqualTo(PERCENT);
        assertThat(response.expiredAt()).isEqualTo(expiredAt);
        assertThat(response.status()).isEqualTo("ISSUED");
    }

    static Stream<Arguments> symbolAndPolicySource() {
        return Stream.of(
                arguments(FIXED, FixedAmountPolicy.class),
                arguments(PERCENT, PercentDiscountPolicy.class)
        );
    }
}
