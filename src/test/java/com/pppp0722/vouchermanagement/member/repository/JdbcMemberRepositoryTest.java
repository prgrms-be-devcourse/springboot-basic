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

import com.pppp0722.vouchermanagement.member.model.Member;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
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
    @DisplayName("createMember() 테스트")
    public void testCreateMember() {
        Optional<Member> member = memberRepository.insert(newMember);

        if (member.isEmpty()) {
            assertThat(false, is(true));
        }

        Optional<Member> readMember = memberRepository.findById(
            newMember.getMemberId());

        if (readMember.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(readMember.get(), samePropertyValuesAs(newMember));
    }

    @Test
    @Order(2)
    @DisplayName("readMembers() 테스트")
    public void testReadMembers() {
        List<Member> members = memberRepository.findAll();
        assertThat(members.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("readMember() 테스트")
    public void testReadMember() {
        Optional<Member> member = memberRepository.findById(newMember.getMemberId());

        if (member.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(member.get(), samePropertyValuesAs(newMember));
    }

    @Test
    @Order(4)
    @DisplayName("updateMember() 테스트")
    public void testUpdateMember() {
        Member updatedMember = new Member(newMember.getMemberId(), "lee");

        Optional<Member> member = memberRepository.update(updatedMember);

        if (member.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(member.get(), not(samePropertyValuesAs(newMember)));
    }

    @Test
    @Order(5)
    @DisplayName("deleteMember() 테스트")
    public void testDeleteMember() {
        Optional<Member> deletedMember = memberRepository.delete(newMember);

        if (deletedMember.isEmpty()) {
            assertThat(false, is(true));
        }

        Optional<Member> retrievedMember = memberRepository.findById(deletedMember.get()
            .getMemberId());

        assertThat(retrievedMember.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("readMember() member 존재 X 예외 테스트")
    public void testReadMemberException() {
        Optional<Member> member = memberRepository.findById(newMember.getMemberId());
        assertThat(member.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("updateMember() member 존재 X 예외 테스트")
    public void testUpdateMemberException() {
        Member updatedMember = new Member(newMember.getMemberId(), "lee");
        Optional<Member> member = memberRepository.update(updatedMember);
        assertThat(member.isEmpty(), is(true));
    }

    @Test
    @Order(8)
    @DisplayName("deleteMember() member 존재 X 예외 테스트")
    public void testDeleteMemberException() {
        Optional<Member> member = memberRepository.delete(newMember);
        assertThat(member.isEmpty(), is(true));
    }
}