package org.programmers.VoucherManagement.member.dao;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.util.Converter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_EXIST_FILE;
@Repository
public class FileMemberRepository implements MemberRepository {
    private static final File file = new File("java/org/programmers/VoucherManagement/file/customer-blacklist.csv");

    @Override
    public List<Member> findAllByMemberStatus() {
        List<Member> memberList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (!line.isBlank()) {
                Member member = Converter.toMember(line);
                memberList.add(member);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(NOT_EXIST_FILE.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return memberList;
    }

}
