package org.programmers.VoucherManagement.global.util;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemberConverter {
    private static final String SEPARATOR = ",";

    private static Member toMember(String line) {
        String[] chunks = line.split(SEPARATOR);

        UUID uuid = UUID.fromString(chunks[0]);
        String name = chunks[1];
        MemberStatus memberStatus = MemberStatus.from(chunks[2]);

        return new Member(uuid, name, memberStatus);
    }

    public static List<Member> toMembers(List<String> lines) {
        return lines.stream()
                .map(s -> MemberConverter.toMember(s))
                .collect(Collectors.toList());
    }
}
