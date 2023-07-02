package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;

import java.util.List;
import java.util.UUID;

public interface VoucherMemberRepository {
    VoucherMember save(VoucherMember voucherMember);

    List<VoucherMember> findVouchersMembersByMemberId(UUID memberId);
}
