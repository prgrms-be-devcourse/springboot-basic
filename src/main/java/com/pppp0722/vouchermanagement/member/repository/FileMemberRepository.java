package com.pppp0722.vouchermanagement.member.repository;

import com.pppp0722.vouchermanagement.CommandLineApplication;
import com.pppp0722.vouchermanagement.member.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileMemberRepository implements MemberRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileMemberRepository.class);
    @Value("${csv.blacklist.file-path}")
    private String filePath;

    // csv 파일에서 blacklist 가져오기
    @Override
    public List<Member> getBlackList() {
        List<Member> blackList = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                UUID memberID = UUID.fromString(splitLine[0]);
                String name = splitLine[1];
                blackList.add(new Member(memberID, name));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("There's no file at {}.", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can't read line from {}.", filePath);
        }

        return blackList;
    }

    public void insert(Member member) {} // 구현 X
}
