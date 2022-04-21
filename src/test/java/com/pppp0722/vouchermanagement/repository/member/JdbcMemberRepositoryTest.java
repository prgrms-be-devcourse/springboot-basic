package com.pppp0722.vouchermanagement.repository.member;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.pppp0722.vouchermanagement.entity.member.Member;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class JdbcMemberRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMemberRepositoryTest.class);

    @Configuration
    @ComponentScan
    static class config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2216/test-voucher_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    JdbcMemberRepository memberRepository;

    EmbeddedMysql embeddedMysql;

    Member newMember;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2216)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
            .start();

        newMember = new Member(UUID.randomUUID(), "kim");
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Member 추가")
    public void testInsert() {
        try {
            memberRepository.createMember(newMember);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}",
                e.getSQLException().getErrorCode(), e);
        }

        Optional<Member> retrievedMember = memberRepository.readMember(
            newMember.getMemberId());
        assertThat(retrievedMember.get(), samePropertyValuesAs(newMember));
    }

    @Test
    @Order(2)
    @DisplayName("Member 조회")
    public void testFindAll() {
        List<Member> members = memberRepository.readMembers();
        assertThat(members.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("Member 삭제")
    public void testDelete() {
        Member deletedMember = memberRepository.deleteMember(newMember);
        Optional<Member> retrievedMember = memberRepository.readMember(deletedMember.getMemberId());
        assertThat(retrievedMember.isEmpty(), is(true));
    }
}