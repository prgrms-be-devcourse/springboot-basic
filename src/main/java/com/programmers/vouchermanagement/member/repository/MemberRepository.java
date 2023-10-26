package com.programmers.vouchermanagement.member.repository;

import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.member.domain.Member;
import com.programmers.vouchermanagement.member.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/resources/customer_blacklist.csv");

    public List<Member> findAllBlack() {

        List<Member> members = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            String data;

            while ((data = br.readLine()) != null) {

                String[] singleData = data.split(",");

                Member member = MemberMapper.toEntity(singleData);
                members.add(member);
            }
        } catch (FileNotFoundException e) {
            throw new FileIOException("File not found. ");

        } catch (IOException e) {
            throw new FileIOException("Member not read due to file issue. ");
        }

        return members;
    }
}
