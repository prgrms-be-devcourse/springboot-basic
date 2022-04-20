package com.example.voucherproject.user.repository;


import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.user.domain.User;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig // 얜 머임
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@DisplayName("USER JDBC REPO TEST")
class UserJdbcRepositoryTest {

    @Autowired
    UserJdbcRepository userJdbcRepository;
    EmbeddedMysql embeddedMysql;

    @Test
    @Order(1)
    @DisplayName("insert")
    void insertTest() {
        var user = makeUser("test1",UserType.NORMAL);
        userJdbcRepository.insert(user);
        assertThat(userJdbcRepository.count()).isEqualTo(1);
    }


    @Test
    @Order(2)
    @DisplayName("find all")
    void findAllTest() {
        var user1 = userJdbcRepository.insert(makeUser("test1", UserType.NORMAL));
        var user2 = userJdbcRepository.insert(makeUser("test2", UserType.NORMAL));
        var user3 = userJdbcRepository.insert(makeUser("test3", UserType.NORMAL));

        var users = userJdbcRepository.findAll();
        assertThat(users).usingRecursiveFieldByFieldElementComparator().contains(user1,user2,user3);
    }

    @Test
    @Order(3)
    @DisplayName("find by type")
    void findByTypeTest() {
        var user1 = userJdbcRepository.insert(makeUser("test1", UserType.NORMAL));
        var user2 = userJdbcRepository.insert(makeUser("test2", UserType.BLACK));
        var user3 = userJdbcRepository.insert(makeUser("test3", UserType.NORMAL));
        var user4 = userJdbcRepository.insert(makeUser("test4", UserType.BLACK));

        var users = userJdbcRepository.findHavingTypeAll(UserType.BLACK);

        assertThat(users).usingRecursiveFieldByFieldElementComparator().contains(user2,user4);
        assertThat(users).usingRecursiveFieldByFieldElementComparator().doesNotContain(user1,user3);

    }

    @Test
    @Order(4)
    @DisplayName("find by id")
    void findById() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        var user1= userJdbcRepository.insert(makeUserWithId(uuid1,"test1", UserType.NORMAL));
        var user2 = userJdbcRepository.insert(makeUserWithId(uuid2,"test2", UserType.BLACK));
        var user3 = userJdbcRepository.insert(makeUserWithId(uuid3,"test3", UserType.NORMAL));
        
        var queryUser1 = userJdbcRepository.findById(uuid1);
        var queryUser2 = userJdbcRepository.findById(uuid2);
        var queryUser3 = userJdbcRepository.findById(uuid3);

        assertThat(user1).usingRecursiveComparison().isEqualTo(queryUser1.get());
        assertThat(user2).usingRecursiveComparison().isEqualTo(queryUser2.get());
        assertThat(user3).usingRecursiveComparison().isEqualTo(queryUser3.get());
    }



    /* helper method */
    private User makeUser(String name, UserType type){
        return makeUserWithId(UUID.randomUUID(), name, type);
    }
    private User makeUserWithId(UUID id, String name, UserType type){
        return new User(id, type, name, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    /* Embedded DB Setting */
    @BeforeAll
    void setup() {
        System.out.println("before al");
        var config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("voucher_app", classPathScript("schema.sql"))
                .start();
    }
    @AfterAll
    void cleanup(){
        embeddedMysql.stop();
    }
    @AfterEach
    void repositoryCleanUp(){
        userJdbcRepository.deleteAll();
    }

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            UUID id;
            var source = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_app")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            source.setMaximumPoolSize(1000);
            source.setMinimumIdle(100);
            return source;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }
        @Bean
        public UserJdbcRepository userJdbcRepository(JdbcTemplate jdbcTemplate){
            return new UserJdbcRepository(jdbcTemplate);
        }
    }
}
