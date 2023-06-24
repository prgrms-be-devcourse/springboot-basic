package kr.co.springbootweeklymission.domain.member.entity.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberStatus {
    WHITE,
    BLACK;
}
