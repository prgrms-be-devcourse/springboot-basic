package org.prgrms.kdt.member.service.dto;

import org.prgrms.kdt.member.domain.MemberStatus;

public record ServiceCreateMemberRequest(String name, MemberStatus status) {
}
