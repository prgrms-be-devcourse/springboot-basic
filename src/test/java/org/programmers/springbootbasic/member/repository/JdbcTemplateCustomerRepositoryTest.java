package org.programmers.springbootbasic.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.springbootbasic.member.domain.Member;
import org.programmers.springbootbasic.member.domain.SignedMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Slf4j
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateCustomerRepositoryTest {

    @Autowired
    private JdbcTemplateCustomerRepository memberRepository;
    @Autowired
    private DataSourceCleaner dataSourceCleaner;

    @Component
    static class DataSourceCleaner {

        private JdbcTemplate template;

        public DataSourceCleaner(DataSource dataSource) {
            this.template = new JdbcTemplate(dataSource);
        }

        public void cleanDataBase() {
            template.update("DELETE from member");
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .addScript("h2/schema.sql")
                    .build();
        }

        @Bean
        public DataSourceCleaner dataSourceCleaner() {
            return new DataSourceCleaner(dataSource());
        }

        @Bean
        JdbcTemplateCustomerRepository customerRepository() {
            return new JdbcTemplateCustomerRepository(dataSource());
        }
    }

    @AfterEach
    void cleanTestData() {
        dataSourceCleaner.cleanDataBase();
    }

    @Test
    @DisplayName("회원 저장, 조회")
    void insertAndFind() {
        var member = new SignedMember("Tester", "testmail@prgms.com");

        var insertedCustomer = memberRepository.insert(member);
        var foundCustomer = memberRepository.findById(insertedCustomer.getMemberId()).get();

        System.out.println("foundCustomer = " + foundCustomer);

        assertThat(insertedCustomer, is(foundCustomer));
    }


    @Test
    @DisplayName("복수의 회원을 조회하는 시험")
    void findAll() {
        var member1 = new SignedMember("Tester1", "testmail1@prgms.com");
        var member2 = new SignedMember("Tester2", "testmail2@prgms.com");
        var member3 = new SignedMember("Tester3", "testmail3@prgms.com");

        List<Member> addedMembers = new ArrayList<>();
        addedMembers.add(member1);
        addedMembers.add(member2);
        addedMembers.add(member3);

        memberRepository.insert(member1);
        memberRepository.insert(member2);
        memberRepository.insert(member3);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size(), is(3));
        assertThat(addedMembers.containsAll(members), is(true));
    }

    @Test
    @DisplayName("회원 삭제하기")
    void insertAndDelete() {
        var member = new SignedMember("Tester", "testmail@prgms.com");

        var insertedMember = memberRepository.insert(member);
        memberRepository.remove(insertedMember.getMemberId());
        assertThat(memberRepository.findById(insertedMember.getMemberId()).isEmpty(), is(true));
    }
}