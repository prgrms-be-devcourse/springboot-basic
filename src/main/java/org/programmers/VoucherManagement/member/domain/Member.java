package org.programmers.VoucherManagement.member.domain;

import org.programmers.VoucherManagement.global.entity.BaseTimeEntity;

public class Member extends BaseTimeEntity {
    private String memberId;
    private String name;
    private MemberStatus memberStatus;

    public Member(String memberId, String name, MemberStatus memberStatus) {
        super();
        this.memberId = memberId;
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void changeMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}
