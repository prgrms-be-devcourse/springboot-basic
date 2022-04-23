package org.programmers.springbootbasic.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode(exclude = {"signedUpAt", "lastLoginAt"})
//TODO: Guest 어떻게 구현할 것인지 고민해보자.
public class SignedMember implements Member {

    public SignedMember(String name, String email) {
        this.name = name;
        this.email = email;
        this.signedUpAt = LocalDateTime.now();
    }

    private Long memberId;
    private final String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime signedUpAt;
    private final List<Voucher> vouchers = new ArrayList<>();

    public SignedMember(Long memberId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime signedUpAt) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.signedUpAt = signedUpAt;
    }
}