package com.prgms.springbootbasic.member.model;

import com.prgms.springbootbasic.global.exception.CantReadFileException;
import com.prgms.springbootbasic.global.util.ExceptionMessage;
import com.prgms.springbootbasic.global.util.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Repository
public class BlackList {

    @Value("${file.blacklist}")
    private String FILE_PATH;

    public List<Member> findBlackList() throws IOException {
        File file = Application.file(FILE_PATH);
        try {
            List<String> memberOfString = Files.readAllLines(file.toPath());
            return memberOfString.stream()
                        .map(m -> getMember(m.split(",")))
                        .toList();
        } catch (Exception e) {
            throw new CantReadFileException(ExceptionMessage.CANT_READ_FILE);
        }
    }

    private Member getMember(String[] member) {
        UUID memberId = UUID.fromString(member[0]);
        String name = member[1];
        return new Member(memberId, name);
    }

}
