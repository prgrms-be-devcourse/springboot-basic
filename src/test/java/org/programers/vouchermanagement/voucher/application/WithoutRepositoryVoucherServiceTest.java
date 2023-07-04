package org.programers.vouchermanagement.voucher.application;

import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ActiveProfiles("test")
@SpringBootTest
class WithoutRepositoryVoucherServiceTest {

    @MockBean
    private VoucherService mockVoucherService;

    @Test
    void 바우처를_저장한다() {
        // given & when & then
        given(mockVoucherService.save(any(VoucherCreationRequest.class)))
                .willReturn(new VoucherResponse(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100));
    }

    @Test
    void 바우처를_아이디로_조회한다() {
        // given & when & then
        given(mockVoucherService.findById(any(UUID.class)))
                .willReturn(new VoucherResponse(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100));
    }

    @Test
    void 바우처를_모두_조회한다() {
        // given & when & then
        given(mockVoucherService.findAll())
                .willReturn(new VouchersResponse(List.of(
                        new VoucherResponse(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100))));
    }

    @Test
    void 바우처를_수정한다() {
        // given & when & then
        willDoNothing().given(mockVoucherService).update(any(VoucherUpdateRequest.class));
    }

    @Test
    void 바우처를_삭제한다() {
        // given & when & then
        willDoNothing().given(mockVoucherService).deleteById(any(UUID.class));
    }
}
