package org.prgrms.kdt.member.dto;

import org.prgrms.kdt.member.domain.MemberStatus;

public record CreateMemberRequest(String name, MemberStatus status) {
}
