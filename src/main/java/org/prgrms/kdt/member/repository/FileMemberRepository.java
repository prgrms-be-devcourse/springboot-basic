package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.util.Loader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class FileMemberRepository implements MemberRepository{
    @Value("${filePath.blackList}")
    private String filePath;
    private Map<UUID, Member> storage;

//    public FileMemberRepository() {
//        this.storage = Loader.loadFileToMemoryMember(filePath);
//    }

    @PostConstruct
    public void init(){
        this.storage = Loader.loadFileToMemoryMember(filePath);
    }

    @Override
    public List<Member> findAllBlackMember(){
        return List.copyOf(storage.values());
    }

    @PreDestroy
    public void fileWrite() {
        Loader.saveMemoryMemberToFile(storage, filePath);
    }
}
