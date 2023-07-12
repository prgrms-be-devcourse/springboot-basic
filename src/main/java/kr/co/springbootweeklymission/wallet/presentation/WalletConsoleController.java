package kr.co.springbootweeklymission.wallet.presentation;

import kr.co.springbootweeklymission.console.InputView;
import kr.co.springbootweeklymission.console.OutputView;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.wallet.application.WalletService;
import kr.co.springbootweeklymission.wallet.presentation.dto.request.WalletReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WalletConsoleController {
    private final WalletService walletService;

    public void createWallet() {
        OutputView.outputCreateVoucherMember();
        final WalletReqDTO.CREATE createWallet = WalletReqDTO.CREATE.builder()
                .voucherId(UUID.fromString(InputView.inputVoucherId()))
                .memberId(UUID.fromString(InputView.inputMemberId()))
                .build();
        walletService.createVoucherMember(createWallet);
    }

    public List<VoucherResDTO.READ> getVouchersByMemberId() {
        UUID memberId = UUID.fromString(InputView.inputMemberId());
        return walletService.getVouchersByMemberId(memberId);
    }

    public MemberResDTO.READ getMemberByVoucherId() {
        UUID voucherId = UUID.fromString(InputView.inputVoucherId());
        return walletService.getMemberByVoucherId(voucherId);
    }


    public void deleteByVoucherIdAndMemberId() {
        OutputView.outputDeleteVoucherMember();
        final WalletReqDTO.DELETE deleteWallet = WalletReqDTO.DELETE.builder()
                .voucherId(UUID.fromString(InputView.inputVoucherId()))
                .memberId(UUID.fromString(InputView.inputMemberId()))
                .build();
        walletService.deleteVoucherMemberByVoucherIdAndMemberId(deleteWallet);
    }
}
