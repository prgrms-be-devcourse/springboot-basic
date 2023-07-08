package org.prgrms.kdt.member.domain;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;
import java.util.Objects;

public enum MemberStatus {
    COMMON("COMMON"),
    BLACK("BLACK");

    private String descripton;

    MemberStatus(String descripton) {
        this.descripton = descripton;
    }

    public static MemberStatus getStatus(String str){
        return Arrays.stream(MemberStatus.values())
                .filter((e) -> Objects.equals(e.descripton, str))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("알맞은 멤버 상태를 찾을 수 없습니다."));
    }

    public String getDescripton() {
        return descripton;
    }
}
