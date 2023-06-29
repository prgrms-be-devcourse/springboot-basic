package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.CantReadFileException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

public class BlackList {

    private static final String FILE_PATH = "customer_blacklist.csv";

    public List<Member> findBlackList() throws IOException {
        File file = VoucherApplication.file(FILE_PATH);
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
