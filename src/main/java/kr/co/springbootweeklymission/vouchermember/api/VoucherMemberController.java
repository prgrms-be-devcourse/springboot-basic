package kr.co.springbootweeklymission.vouchermember.api;

import kr.co.springbootweeklymission.vouchermember.application.VoucherMemberService;
import kr.co.springbootweeklymission.vouchermember.domain.model.VoucherMemberReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherMemberController {
    private final VoucherMemberService voucherMemberService;

    public void createVoucherMember(VoucherMemberReqDTO.CREATE create) {
        voucherMemberService.createVoucherMember(create);
    }
}
