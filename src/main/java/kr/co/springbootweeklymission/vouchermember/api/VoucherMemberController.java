package kr.co.springbootweeklymission.vouchermember.api;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.vouchermember.api.dto.request.VoucherMemberReqDTO;
import kr.co.springbootweeklymission.vouchermember.application.VoucherMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherMemberController {
    private final VoucherMemberService voucherMemberService;

    public void createVoucherMember(VoucherMemberReqDTO.CREATE create) {
        voucherMemberService.createVoucherMember(create);
    }

    public List<VoucherResDTO.READ> getVouchersByMemberId(UUID memberId) {
        return voucherMemberService.getVouchersByMemberId(memberId);
    }

    public List<MemberResDTO.READ> getMembersByVoucherId(UUID voucherId) {
        return voucherMemberService.getMembersByVoucherId(voucherId);
    }


    public void deleteVoucherMemberByVoucherIdAndMemberId(VoucherMemberReqDTO.DELETE delete) {
        voucherMemberService.deleteVoucherMemberByVoucherIdAndMemberId(delete);
    }
}
