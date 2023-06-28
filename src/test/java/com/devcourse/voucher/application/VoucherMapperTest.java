package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.domain.DiscountPolicy;
import com.devcourse.voucher.domain.FixedAmountPolicy;
import com.devcourse.voucher.domain.PercentDiscountPolicy;
import com.devcourse.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.devcourse.voucher.domain.VoucherType.FIXED;
import static com.devcourse.voucher.domain.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VoucherMapperTest {
    private final VoucherMapper voucherMapper = new VoucherMapper();
    private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

    @ParameterizedTest
    @DisplayName("매핑된 결과물이 요청에 맞는 결과물이어야 한다.")
    @MethodSource("symbolWithPolicyClass")
    void mapToFixedTest(String symbol, Class<? extends DiscountPolicy> clazz) {
        // given
        int discount = 100;
        CreateVoucherRequest request = new CreateVoucherRequest(symbol, discount, expiredAt);

        // when
        Voucher voucher = voucherMapper.mapFrom(request);

        // then
        assertThat(voucher.getDiscountPolicy()).isInstanceOf(clazz);
        assertThat(voucher.getExpireAt()).isEqualTo(expiredAt);
        assertThat(voucher.isUsed()).isFalse();
    }

    @Test
    @DisplayName("DTO 리스트로 변환될 때 크기와 내용물에 변화가 없어야 한다.")
    void toResponseListTest() {
        // given
        List<Voucher> vouchers = IntStream.range(0, 5)
                .mapToObj(discount -> Voucher.percent(discount, expiredAt))
                .toList();

        // when
        List<GetVoucherResponse> responseList = voucherMapper.toResponseList(vouchers);

        // then
        assertThat(responseList).isNotEmpty().hasSize(5);
        responseList.forEach(response -> {
            assertThat(response.type()).isEqualTo(PERCENT);
            assertThat(response.expiredAt()).isEqualTo(expiredAt);
        });
    }

    static Stream<Arguments> symbolWithPolicyClass() {
        return Stream.of(
                arguments(FIXED.getSymbol(), FixedAmountPolicy.class),
                arguments(PERCENT.getSymbol(), PercentDiscountPolicy.class)
        );
    }
}
