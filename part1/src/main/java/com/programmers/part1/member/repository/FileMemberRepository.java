package com.programmers.part1.member.repository;

import com.programmers.part1.member.entity.Member;
import com.programmers.part1.member.entity.MemberDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FileMemberRepository implements MemberRepository<UUID, MemberDto>{

    private static final String PATH = "src/main/resources/customer_blacklist";
    private final ResourceLoader resourceLoader;
    private Resource resource;
    private Reader reader;

    public FileMemberRepository(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        setResource(this.resourceLoader);
        setReader(this.resource);
    }

    @Override
    public List<MemberDto> findAllBlackMember() throws IOException {
        //Member record
        String[] rows = FileCopyUtils.copyToString(reader).split("\n");

        List<MemberDto> blackMembers = new ArrayList<>();
        for(String row : rows){
            String[] column = row.split(",");
            if(column[2].equals("black")) // filter 블랙리스트 멤버만
                blackMembers.add(new MemberDto(Long.parseLong(column[0]),column[1],column[2]));
        }
        return blackMembers;
    }

    private void setResource(ResourceLoader resourceLoader){
        this.resource = resourceLoader.getResource(PATH);
    }

    private void setReader(Resource resource) throws IOException {
        this.reader = new InputStreamReader(resource.getInputStream(),StandardCharsets.UTF_8);
    }

}
