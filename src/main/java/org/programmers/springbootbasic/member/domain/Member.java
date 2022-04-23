package org.programmers.springbootbasic.member.domain;

import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.List;

public interface Member {

    public Long getMemberId();

    public String getName();

    public String getEmail();

    public LocalDateTime getLastLoginAt();

    public LocalDateTime getSignedUpAt();

    public List<Voucher> getVouchers();

    //TODO: setter 지우자
    public void setMemberId(Long memberId);
}