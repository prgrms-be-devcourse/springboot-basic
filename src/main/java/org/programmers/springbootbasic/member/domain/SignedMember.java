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
@EqualsAndHashCode(exclude = "signedUpAt")
//TODO: PR 포인트: guest 주문 등을 Customer 자식 클래스로 삼을 지 고민, 개인적으로는 CUSTOMER 정보가 null 인 ORDER 기반 조회가 낫지 않나 싶었음, 요구사항에 대해 고민됨
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

    @Override
    public Long getMemberId() {
        return memberId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    @Override
    public LocalDateTime getSignedUpAt() {
        return signedUpAt;
    }

    @Override
    public List<Voucher> getVouchers() {
        return vouchers;
    }
}