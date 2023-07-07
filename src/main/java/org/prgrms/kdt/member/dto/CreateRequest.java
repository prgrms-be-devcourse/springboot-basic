package org.prgrms.kdt.member.dto;

import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.UUID;

public record CreateRequest(UUID memberId, String name, MemberStatus status) {
}
