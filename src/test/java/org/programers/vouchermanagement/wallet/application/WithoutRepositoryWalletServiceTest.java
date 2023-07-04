package org.programers.vouchermanagement.wallet.application;

import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.wallet.dto.request.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.response.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletsResponse;
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
class WithoutRepositoryWalletServiceTest {

    private static WalletResponse response = new WalletResponse(
            UUID.randomUUID(),
            new VoucherResponse(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100),
            new MemberResponse(UUID.randomUUID(), MemberStatus.NORMAL));

    @MockBean
    private WalletService mockWalletService;

    @Test
    void 지갑를_저장한다() {
        // given & when & then
        given(mockWalletService.save(any(WalletCreationRequest.class)))
                .willReturn(response);
    }

    @Test
    void 지갑를_아이디로_조회한다() {
        // given & when & then
        given(mockWalletService.findById(any(UUID.class)))
                .willReturn(response);
    }

    @Test
    void 지갑를_모두_조회한다() {
        // given & when & then
        given(mockWalletService.findAll())
                .willReturn(new WalletsResponse(List.of(response)));
    }

    @Test
    void 지갑를_삭제한다() {
        // given & when & then
        willDoNothing().given(mockWalletService).deleteById(any(UUID.class));
    }
}
