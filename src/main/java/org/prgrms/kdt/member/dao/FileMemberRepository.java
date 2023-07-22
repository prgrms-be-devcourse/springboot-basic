package org.prgrms.kdt.member.dao;

import org.prgrms.kdt.member.domain.Member;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class FileMemberRepository implements MemberRepository {
    private final Map<UUID, Member> storage;
    private final MemberLoader memberLoader;

    public FileMemberRepository(MemberLoader memberLoader) {
        this.memberLoader = memberLoader;
        this.storage = this.memberLoader.loadFileToMemoryMember();
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
        memberLoader.saveMemoryMemberToFile(storage);
    }
}
