package com.weeklyMission.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
class DBMemberRepositoryTest {

    @TestConfiguration
    static class Config{

        @Bean
        public DataSource dataSource(){
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:h2:mem:test")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("")
                .type(HikariDataSource.class)
                .build();

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            jdbcTemplate.getJdbcTemplate().execute("CREATE TABLE member (member_id BINARY(16) PRIMARY KEY, name varchar(20) not null, age int not null, reason varchar(50))");

            jdbcTemplate.update("INSERT INTO member(member_id, name, age, reason) values (:member_id, :name, :age, :reason)", new HashMap(){{
                put("member_id", UUID.randomUUID());
                put("name", "김강훈");
                put("age", "26");
                put("reason", "욕설");
            }});

            return dataSource;
        }

        @Bean
        public DBMemberRepository dbMemberRepository(DataSource dataSource){
            return new DBMemberRepository(dataSource);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    DBMemberRepository dbMemberRepository;

    @Test
    void findAllTest() {
        assertThat(dbMemberRepository.findAll().size()).isEqualTo(1);
        assertThat(dbMemberRepository.findAll().get(0).name()).isEqualTo("김강훈");
    }
}