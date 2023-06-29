package org.programmers.VoucherManagement.member.dao;

import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.global.util.MemberConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.programmers.VoucherManagement.global.exception.FileExceptionMessage.CAN_NOT_READ_LINE;
import static org.programmers.VoucherManagement.global.exception.FileExceptionMessage.NOT_EXIST_FILE;

@Component
public class FileMemberRepository implements MemberRepository {
    private final File file;
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private FileMemberRepository(@Value("${app.path.file}") String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public List<Member> findAllByMemberStatus() {
        List<Member> memberList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
//            List<String> lines = new ArrayList();

            while ((line = br.readLine()) != null) {
                Member member = MemberConverter.toMember(line);
                memberList.add(member);
            }
        } catch (FileNotFoundException ex) {
            logger.info(NOT_EXIST_FILE.getMessage());
            throw new RuntimeException(NOT_EXIST_FILE.getMessage(), ex);
        } catch (IOException ex) {
            logger.info(CAN_NOT_READ_LINE.getMessage());
            throw new RuntimeException(CAN_NOT_READ_LINE.getMessage(), ex);
        }

        return memberList;
    }
}
