package com.weeklyMission.member.repository;

import com.weeklyMission.member.domain.Member;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileMemberRepository {
    private final String path;

    private final Map<Long, Member> storage;

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
                Member member = new Member(Long.parseLong(dataSplit[0]), dataSplit[1], Integer.parseInt(dataSplit[2]), dataSplit[3]);
                storage.put(member.voucherId(), member);
            }
        }catch(IOException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Member> getBlackList() {
        return new ArrayList<>(storage.values());
    }
}
