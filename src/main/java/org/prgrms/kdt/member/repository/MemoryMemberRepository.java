package org.prgrms.kdt.member.repository;

import org.prgrms.kdt.member.domain.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Repository
@Profile("dev")
public class MemoryMemberRepository implements MemberRepository {

    private final Map<UUID, Member> storage;

    public MemoryMemberRepository() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public Map<UUID, Member> findByAllBlackList() {
        return storage;
    }

    @Override
    public void init(List<String> lines) {
        IntStream
                .range(0, lines.size())
                .filter(i -> i != 0)
                .forEach(i -> storage.put(UUID.randomUUID(), Member.of(lines.get(i))));
    }


}
