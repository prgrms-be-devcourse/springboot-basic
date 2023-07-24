package org.weekly.weekly.voucher.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.weekly.weekly.ui.exception.InputException;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.request.VoucherInfoRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CustomerDto 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class VoucherDtoTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "10, 10",
            "50, 12",
            "70, 123"
    })
    void 고정바우처_생성_성공_테스트(String userInput) {
        // Given
        VoucherInfoRequest voucherInfoRequest = VoucherInfoRequest.of(userInput);

        // When
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(voucherInfoRequest, DiscountType.FIXED);

        // Then
        assertThat(voucherCreationRequest).isNotNull();
        assertThat(voucherCreationRequest.getDiscountType()).isEqualTo(DiscountType.FIXED);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "10, 10",
            "50, 12",
            "70, 123"
    })
    void 퍼센트바우처_생성_성공_테스트(String userInput) {
        // Given
        VoucherInfoRequest voucherInfoRequest = VoucherInfoRequest.of(userInput);

        // When
        VoucherCreationRequest voucherCreationRequest = new VoucherCreationRequest(voucherInfoRequest, DiscountType.PERCENT);

        // Then
        assertThat(voucherCreationRequest).isNotNull();
        assertThat(voucherCreationRequest.getDiscountType()).isEqualTo(DiscountType.PERCENT);
    }

    @ParameterizedTest
    @ValueSource(strings = {" , ",
            "가나다, ",
            ", 나다라"
    })
    void 바우처_잘못된_입력으로_실패_테스트(String userInput) {
        // When + Then
        assertThatThrownBy(() -> VoucherInfoRequest.of(userInput))
                .isInstanceOf(InputException.class);

    }

    @Test
    void 바우처_생성요청에_대한_반환_메세지_테스트() {
        // Given
        Voucher voucher = Voucher.of(UUID.randomUUID(),
                1000L,
                LocalDate.now(),
                12,
                DiscountType.FIXED);

        VoucherCreationResponse creationResponse = new VoucherCreationResponse(voucher);

        assertThat(creationResponse.result())
                .isEqualTo(MessageFormat.format("[ID: {0}, 금액: {1}, 등록일자: {2}, 유효기간: {3}]",
                        voucher.getVoucherId(), voucher.getAmount(), voucher.getRegistrationDate(), voucher.getExpirationDate()));
    }
}
