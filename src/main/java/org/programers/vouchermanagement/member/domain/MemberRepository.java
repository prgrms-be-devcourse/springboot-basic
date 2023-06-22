package org.programers.vouchermanagement.member.domain;

import org.programers.vouchermanagement.util.Converter;
import org.programers.vouchermanagement.voucher.exception.NoSuchVoucherException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private static final File file = new File("src/main/resources/blacklist.csv");

    public List<Member> findAll() {
        List<Member> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Member member = Converter.toMember(line);
                result.add(member);
            }
            reader.close();
            return result;
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 조회되지 않았습니다.");
        }
    }
}
