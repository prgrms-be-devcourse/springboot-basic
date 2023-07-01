package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.MemberLoader;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.VoucherLoader;
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
