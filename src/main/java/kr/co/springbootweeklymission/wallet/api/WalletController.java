package kr.co.springbootweeklymission.wallet.api;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.wallet.api.dto.request.WalletReqDTO;
import kr.co.springbootweeklymission.wallet.application.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    public void createVoucherMember(WalletReqDTO.CREATE create) {
        walletService.createVoucherMember(create);
    }

    public List<VoucherResDTO.READ> getVouchersByMemberId(UUID memberId) {
        return walletService.getVouchersByMemberId(memberId);
    }

    public MemberResDTO.READ getMemberByVoucherId(UUID voucherId) {
        return walletService.getMemberByVoucherId(voucherId);
    }


    public void deleteVoucherMemberByVoucherIdAndMemberId(WalletReqDTO.DELETE delete) {
        walletService.deleteVoucherMemberByVoucherIdAndMemberId(delete);
    }
}
