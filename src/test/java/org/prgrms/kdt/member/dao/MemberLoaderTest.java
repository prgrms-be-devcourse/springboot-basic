package org.prgrms.kdt.member.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MemberLoaderTest {
    MemberLoader memberLoader;

    @BeforeEach
    void setup() {
        memberLoader = new MemberLoader("src/test/resources/test_customer_blacklist.csv");
    }


    @ParameterizedTest
    @MethodSource("mamberSource")
    @DisplayName("메모리의 블랙리스트들 파일에 저장 후 저장된 파일 확인")
    void saveMemoryMemberToFile(Member blackMember1, Member blackMember2) {
        //given
        Map<UUID, Member> memberMap = new ConcurrentHashMap<>();
        memberMap.put(blackMember1.getMemberId(), blackMember1);
        memberMap.put(blackMember2.getMemberId(), blackMember2);

        //when
        memberLoader.saveMemoryMemberToFile(memberMap);
        Map<UUID, Member> foundMembers = memberLoader.loadFileToMemoryMember();

        //then
        assertThat(foundMembers.size(), is(2));
    }

    static Stream<Member[]> mamberSource() {
        Member member1 = new Member(UUID.randomUUID(), "haha", MemberStatus.BLACK);
        Member member2 = new Member(UUID.randomUUID(), "hoho", MemberStatus.BLACK);
        return Stream.of(new Member[][]{{member1, member2}});
    }

}