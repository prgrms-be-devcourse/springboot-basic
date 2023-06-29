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
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileMemberRepository implements MemberRepository {
    @Value("${filePath.blackList}")
    private String filePath;
    private final Map<UUID, Member> storage = new ConcurrentHashMap<>();

    @PostConstruct
    public void load() {
        List<Member> members = Loader.loadFileToMemoryMember(filePath);
        members.forEach(m -> storage.put(m.getMemberId(), m));
    }

    @Override
    public Member insert(Member member) {
        storage.put(member.getMemberId(), member);
        return member;
    }

    @Override
    public List<Member> findAllBlackMember() {
        return List.copyOf(storage.values());
    }

    @PreDestroy
    public void fileWrite() {
        Loader.saveMemoryMemberToFile(storage, filePath);
    }
}
