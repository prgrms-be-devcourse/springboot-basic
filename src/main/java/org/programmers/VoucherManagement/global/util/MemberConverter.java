package org.programmers.VoucherManagement.global.util;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {
    private static final String SEPARATOR = ",";

    private MemberConverter() {
    }

    public static Member toMember(String line) {
        String[] chunks = line.split(SEPARATOR);

        String id = chunks[0];
        String name = chunks[1];
        MemberStatus memberStatus = MemberStatus.from(chunks[2]);

        return new Member(id, name, memberStatus);
    }

    public static List<Member> toMembers(List<String> lines) {
        return lines.stream()
                .map(MemberConverter::toMember)
                .collect(Collectors.toList());
    }
}
