package com.programmers.part1.member.repository;

import com.programmers.part1.member.entity.MemberDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Repository
public class FileMemberRepository implements MemberRepository<UUID, MemberDto>{

    private static final String PATH = "customer_blacklist.csv";
    ResourceLoader resourceLoader;

    public FileMemberRepository(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<MemberDto> findAllBlackMember() throws IOException {
        Resource resource = this.resourceLoader.getResource(PATH);
        Reader reader = new InputStreamReader(resource.getInputStream(),StandardCharsets.UTF_8);

        //회원 record
        String[] rows = FileCopyUtils.copyToString(reader).split("\n");

        List<MemberDto> blackMembers = new ArrayList<>();
        for(String row : rows){
            String[] column = row.split(",");
            if(column[2].equals("Black")) // filter 블랙리스트 멤버만
                blackMembers.add(new MemberDto(Long.parseLong(column[0]),column[1],column[2]));

        }

        return blackMembers;
    }

}
