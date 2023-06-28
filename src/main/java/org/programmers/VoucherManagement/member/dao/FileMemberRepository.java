package org.programmers.VoucherManagement.member.dao;

import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.global.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.programmers.VoucherManagement.global.exception.FileExceptionMessage.CAN_NOT_READ_LINE;
import static org.programmers.VoucherManagement.global.exception.FileExceptionMessage.NOT_EXIST_FILE;

@Repository
public class FileMemberRepository implements MemberRepository {
    private static final File file = new File("src/main/resources/file/customer-blacklist.csv");
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    @Override
    public List<Member> findAllByMemberStatus() {
        List<Member> memberList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member member = Converter.toMember(line);
                memberList.add(member);
            }
        } catch (FileNotFoundException ex) {
            logger.info(NOT_EXIST_FILE.getMessage());
            throw new RuntimeException(NOT_EXIST_FILE.getMessage());
        } catch (IOException ex) {
            logger.info(CAN_NOT_READ_LINE.getMessage());
            throw new RuntimeException(CAN_NOT_READ_LINE.getMessage());
        }
        return memberList;
    }
}
