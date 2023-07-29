package org.prgrms.kdt.member.controller.dto;

import org.prgrms.kdt.member.domain.MemberStatus;

public record CreateMemberApiRequest(String name, MemberStatus status) {
}
