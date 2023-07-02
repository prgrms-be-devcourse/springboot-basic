package kr.co.springbootweeklymission.vouchermember.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import kr.co.springbootweeklymission.vouchermember.domain.model.VoucherMemberReqDTO;
import kr.co.springbootweeklymission.vouchermember.domain.repository.VoucherMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherMemberService {
    private final VoucherMemberRepository voucherMemberRepository;
    private final VoucherRepository voucherRepository;
    private final MemberRepository memberRepository;

    public void createVoucherMember(VoucherMemberReqDTO.CREATE create) {
        final Voucher voucher = voucherRepository.findById(create.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        final Member member = memberRepository.findById(create.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        final VoucherMember saveVoucherMember = VoucherMember.toVoucherMember(voucher, member);
        voucherMemberRepository.save(saveVoucherMember);
    }
}
