package com.devcourse.voucher.application;

import com.devcourse.voucher.domain.Voucher;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

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

    private final LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

    @ParameterizedTest
    @DisplayName("생성 요청에 따라 repository를 한번만 호출해야 한다.")
    @CsvSource({"5000, FIXED",
                "50, PERCENT"})
    void createTest(int discount, Voucher.Type type) {
        // given
        given(voucherRepository.save(any())).willReturn(any());

        // when
        voucherService.create(discount, expiredAt, type);

        // then
        then(voucherRepository).should(times(1)).save(any());
    }
}
