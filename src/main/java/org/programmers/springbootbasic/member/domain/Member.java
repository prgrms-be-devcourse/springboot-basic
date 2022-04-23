package org.programmers.springbootbasic.member.domain;

import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.List;

public interface Member {

    Long getMemberId();

    String getName();

    String getEmail();

    LocalDateTime getLastLoginAt();

    LocalDateTime getSignedUpAt();

    List<Voucher> getVouchers();
}