package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.member.domain.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("file")
@Component
public class BlackListRepository {
    private final Map<UUID, Member> storage;
    private final MemberLoader memberLoader;

    public BlackListRepository(MemberLoader memberLoader) {
        this.memberLoader = memberLoader;
        this.storage = this.memberLoader.loadFileToMemoryMember();
    }

    public Member insert(Member member) {
        storage.put(member.getMemberId(), member);
        return member;
    }

    public List<Member> findAllBlackMember() {
        return List.copyOf(storage.values());
    }

    @PreDestroy
    public void fileWrite() {
        memberLoader.saveMemoryMemberToFile(storage);
    }
}
