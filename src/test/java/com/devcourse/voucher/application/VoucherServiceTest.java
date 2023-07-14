package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static com.devcourse.voucher.domain.Voucher.Type.FIXED;
import static com.devcourse.voucher.domain.Voucher.Type.PERCENT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class VoucherServiceTest {
    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    private final LocalDateTime invalidExpiration = LocalDateTime.of(2022, 1, 1, 0, 0);
    private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

    @Test
    @DisplayName("0보다 작은 할인량을 받으면 예외가 발생한다..")
    void validateDiscountAmountTest() {
        // given
        int discountAmount = -1;
        CreateVoucherRequest request = new CreateVoucherRequest(discountAmount, expiredAt, FIXED);

        // when, then
        assertThatThrownBy(() -> voucherService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("0보다 작거나 100보다 큰 할인률을 받으면 예외가 발생한다.")
    @ValueSource(ints = {-1, 101})
    void validateDiscountRateTest(int discountRate) {
        // given
        CreateVoucherRequest request = new CreateVoucherRequest(discountRate, expiredAt, PERCENT);

        // when, then
        assertThatThrownBy(() -> voucherService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재보다 과거인 만료일을 받으면 예외가 발생한다.")
    void validateExpirationTest() {
        // given
        int discountAmount = 1_500;
        CreateVoucherRequest request = new CreateVoucherRequest(discountAmount, invalidExpiration, FIXED);

        // when, then
        assertThatThrownBy(() -> voucherService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("생성 요청이 정상적이라면 검증을 통과하고 repository의 save를 호출한다.")
    @CsvSource({"5000, FIXED",
                "50, PERCENT"})
    void createTest(int discount, Voucher.Type type) {
        // given
        Voucher any = any(Voucher.class);
        CreateVoucherRequest request = new CreateVoucherRequest(discount, expiredAt, type);
        given(voucherRepository.save(any)).willReturn(any);

        // when
        voucherService.create(request);

        // then
        then(voucherRepository).should(times(1)).save(any);
    }
}
