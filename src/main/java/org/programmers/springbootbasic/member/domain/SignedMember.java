package org.programmers.springbootbasic.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
public class SignedMember implements Member {

    public SignedMember(String name, String email) {
        this.name = name;
        this.email = email;
        this.signedUpAt = Timestamp.from(java.time.Instant.now());
    }

    private Long memberId;
    private final String name;
    private final String email;
    private Timestamp lastLoginAt;
    private final Timestamp signedUpAt;
    private final List<Voucher> vouchers = new ArrayList<>();

    public SignedMember(Long memberId, String name, String email, Timestamp lastLoginAt, Timestamp signedUpAt) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.signedUpAt = signedUpAt;
    }
}