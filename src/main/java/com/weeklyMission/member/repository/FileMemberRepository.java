package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileMemberRepository implements MemberRepository{
    private final String path;

    private final String seperator = ",";

    private final Map<UUID, Member> storage;

    public FileMemberRepository(@Value("${filePath.repository.member}") String path) {
        this.path = System.getProperty("user.dir") + path;
        this.storage = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void init(){
        loadFile();
    }

    private void loadFile() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))){
            String data;
            while((data=br.readLine())!=null){
                String[] dataSplit = data.split(",");
                Member member = new Member(UUID.fromString(dataSplit[0]), dataSplit[1], Integer.parseInt(dataSplit[2]), dataSplit[3]);
                storage.put(member.memberId(), member);
            }
        }catch(IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void close(){
        writeFile();
    }

    private void writeFile() {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8))){
            for (Member member : storage.values()) {
                bw.write(member.memberId() + seperator + member.name() + seperator + member.age() + seperator + member.reason());
                bw.newLine();
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Member save(Member member) {
        storage.put(member.memberId(), member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());
    }
}
