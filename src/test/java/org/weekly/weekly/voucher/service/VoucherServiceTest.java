package org.weekly.weekly.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.request.VoucherInfoRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.repository.VoucherRepository;
import org.weekly.weekly.voucher.service.VoucherService;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@DisplayName("VoucherService 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    void 고정_바우처_생성_성공_테스트() {
        // Given
        Long amount = 1000000L;
        Long expire = 1L;
        LocalDate registrationDate = LocalDate.now();
        LocalDate expirationDate = LocalDate.now().plusDays(expire);
        DiscountType discount = DiscountType.FIXED;

        VoucherInfoRequest voucherFixedInfo = new VoucherInfoRequest(amount, expire);
        VoucherCreationRequest fixedRequest = new VoucherCreationRequest(voucherFixedInfo, discount);
        Voucher mockVoucehr = new Voucher(UUID.randomUUID(),
                amount,
                registrationDate,
                expirationDate,
                discount.getNewInstance());

        given(voucherRepository.insert(any(Voucher.class))).willReturn(mockVoucehr);

        // When
        VoucherCreationResponse voucherCreationResponse = voucherService.insertVoucher(fixedRequest);

        // Then;
        assertThat(voucherCreationResponse).isNotNull();
    }

    @Test
    void 할인_바우처_생성_성공_테스트() {
        // Given
        Long percnet = 10L;
        Long expire = 1L;
        LocalDate registrationDate = LocalDate.now();
        LocalDate expirationDate = LocalDate.now().plusDays(expire);
        DiscountType discount = DiscountType.PERCENT;

        VoucherInfoRequest voucherPercentInfo = new VoucherInfoRequest(percnet, expire);
        VoucherCreationRequest percentRequest = new VoucherCreationRequest(voucherPercentInfo, discount);
        Voucher mockVoucehr = new Voucher(UUID.randomUUID(),
                percnet,
                registrationDate,
                expirationDate,
                discount.getNewInstance());

        given(voucherRepository.insert(any(Voucher.class))).willReturn(mockVoucehr);

        // When
        VoucherCreationResponse voucherCreationResponse = voucherService.insertVoucher(percentRequest);

        // Then;
        assertThat(voucherCreationResponse).isNotNull();
    }
}
