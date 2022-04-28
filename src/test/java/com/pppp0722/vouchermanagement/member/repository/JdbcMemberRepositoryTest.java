package com.pppp0722.vouchermanagement.member.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pppp0722.vouchermanagement.member.model.Member;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("JdbcMemberRepositoryTest 단위 테스트")
class JdbcMemberRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    JdbcMemberRepository memberRepository;

    private final Member newMember = new Member(UUID.randomUUID(), "kim");

    @Test
    @Order(1)
    @DisplayName("멤버를 생성할 수 있다.")
    public void testCreateMember() {
        Member member = memberRepository.insert(newMember);

        Optional<Member> readMember = memberRepository.findById(
            member.getMemberId());

        if (readMember.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(readMember.get(), samePropertyValuesAs(newMember));
    }

    @Test
    @Order(2)
    @DisplayName("모든 멤버들을 읽어올 수 있다.")
    public void testReadMembers() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("멤버를 읽어올 수 있다.")
    public void testReadMember() {
        Optional<Member> member = memberRepository.findById(newMember.getMemberId());

        if (member.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(member.get(), samePropertyValuesAs(newMember));
    }

    @Test
    @Order(4)
    @DisplayName("멤버를 업데이트할 수 있다.")
    public void testUpdateMember() {
        Member newMember2 = new Member(newMember.getMemberId(), "lee");
        Member updatedMember = memberRepository.update(newMember2);

        assertThat(updatedMember, not(samePropertyValuesAs(newMember)));
    }

    @Test
    @Order(5)
    @DisplayName("멤버를 삭제할 수 있다.")
    public void testDeleteMember() {
        memberRepository.delete(newMember);
        List<Member> members = memberRepository.findAll();

        assertThat(members.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("멤버를 읽어올 때 아이디가 존재하지 않으면 empty를 반환한다.")
    public void testReadMemberException() {
        Optional<Member> member = memberRepository.findById(newMember.getMemberId());

        assertThat(member.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("멤버를 업데이트할 때 아이디가 존재하지 않으면 예외가 발생한다.")
    public void testUpdateMemberException() {
        Member updatedMember = new Member(newMember.getMemberId(), "lee");
        assertThrows(RuntimeException.class, () -> {
            memberRepository.update(updatedMember);
        });
    }

    @Test
    @Order(8)
    @DisplayName("멤버를 삭제할 때 아이디가 존재하지 않으면 예외가 발생한다.")
    public void testDeleteMemberException() {
        assertThrows(RuntimeException.class, () -> {
            memberRepository.delete(newMember);
        });
    }
}