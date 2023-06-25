package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.util.Converter;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileMemberRepository implements MemberRepository{
    private final String filePath = "src/main/resources/customer_blacklist.csv";
    @Override
    public List<Member> findAllBlackMember() throws IOException {
        List<Member> blackList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        while ((line = reader.readLine()) != null) {
            String[] record = Converter.stringToArray(line, ",");
            blackList.add(Converter.stringArrToMember(record, MemberStatus.BLACK));
        }
        return blackList;
    }
}
