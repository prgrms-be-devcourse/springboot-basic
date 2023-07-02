package kr.co.springbootweeklymission.vouchermember.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.vouchermember.api.dto.request.VoucherMemberReqDTO;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import kr.co.springbootweeklymission.vouchermember.domain.repository.VoucherMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoucherMemberService {
    private final VoucherMemberRepository voucherMemberRepository;
    private final VoucherRepository voucherRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createVoucherMember(VoucherMemberReqDTO.CREATE create) {
        final Voucher voucher = voucherRepository.findById(create.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        final Member member = memberRepository.findById(create.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        final VoucherMember saveVoucherMember = VoucherMember.toVoucherMember(voucher, member);
        voucherMemberRepository.save(saveVoucherMember);
    }

    public List<VoucherResDTO.READ> getVouchersByMemberId(UUID memberId) {
        final List<VoucherMember> voucherMembers = voucherMemberRepository.findVouchersMembersByMemberId(memberId);
        return voucherMembers.stream()
                .map(VoucherResDTO.READ::toVoucherReadDto)
                .toList();
    }

    public List<MemberResDTO.READ> getMembersByVoucherId(UUID voucherId) {
        final List<VoucherMember> voucherMembers = voucherMemberRepository.findVouchersMembersByVoucherId(voucherId);
        return voucherMembers.stream()
                .map(MemberResDTO.READ::toMemberReadDto)
                .toList();
    }

    @Transactional
    public void deleteVoucherMemberByVoucherIdAndMemberId(VoucherMemberReqDTO.DELETE delete) {
        final Voucher voucher = voucherRepository.findById(delete.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER));
        final Member member = memberRepository.findById(delete.getMemberId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        voucherMemberRepository.deleteByVoucherIdAndMemberId(voucher.getVoucherId(), member.getMemberId());
    }
}

