package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;

public interface VoucherMemberRepository {
    VoucherMember save(VoucherMember voucherMember);
}
