package org.programmers.VoucherManagement.global.util;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public class Converter {
    private static final String SEPARATOR = ",";
    public static Member toMember(String line){
        String[] chunks = line.split(SEPARATOR);

        UUID uuid = UUID.fromString(chunks[0]);
        String name = chunks[1];
        MemberStatus memberStatus = MemberStatus.from(chunks[2]);

        return new Member(uuid,name,memberStatus);
    }
}
