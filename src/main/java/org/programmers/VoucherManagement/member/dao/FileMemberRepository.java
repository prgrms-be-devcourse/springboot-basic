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
    private static final File file = new File("src/main/java/org/programmers/VoucherManagement/file/customer-blacklist.csv");

    @Override
    public List<Member> findAllByMemberStatus() {
        List<Member> memberList = new ArrayList<>();
        boolean isFileEnd = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member member = Converter.toMember(line);
                memberList.add(member);
            }
            isFileEnd = true;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(NOT_EXIST_FILE.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return memberList;
    }
}
