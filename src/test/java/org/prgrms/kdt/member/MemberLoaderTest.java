package org.prgrms.kdt.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MemberLoaderTest {
    MemberLoader memberLoader;

    @BeforeEach
    void setup() {
        memberLoader = new MemberLoader("src/test/test_customer_blacklist.csv");
    }

    @Test
    void 파일에_블랙멤버_저장_후_저장확인() {
        //given
        Member blackMember1 = new Member(UUID.randomUUID(), "haha", MemberStatus.BLACK);
        Member blackMember2 = new Member(UUID.randomUUID(), "hoho", MemberStatus.BLACK);
        Map<UUID, Member> memberMap = new ConcurrentHashMap<>();
        memberMap.put(blackMember1.getMemberId(), blackMember1);
        memberMap.put(blackMember2.getMemberId(), blackMember2);

        //when
        memberLoader.saveMemoryMemberToFile(memberMap);
        Map<UUID, Member> foundMembers = memberLoader.loadFileToMemoryMember();

        //then
        assertThat(foundMembers.size(), is(2));
    }

}