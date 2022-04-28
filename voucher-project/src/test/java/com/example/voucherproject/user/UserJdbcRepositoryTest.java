package com.example.voucherproject.user;


import com.example.voucherproject.TestRepositoryConfig;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.repository.UserJdbcRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.common.Helper.UserHelper.makeUser;
import static com.example.voucherproject.common.Helper.UserHelper.makeUserWithId;
import static com.example.voucherproject.user.model.UserType.BLACK;
import static com.example.voucherproject.user.model.UserType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = TestRepositoryConfig.class)
@DisplayName("User Persistence Layer Test")
class UserJdbcRepositoryTest {

    @Autowired
    UserJdbcRepository userJdbcRepository;

    @Nested
    @DisplayName("INSERT TEST")
    class InsertUserTest {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 10})
        @DisplayName("[insert:성공] 유저 N명 정상 저장")
        void basicInsertTest(int n) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                users.add(userJdbcRepository.insert(makeUser("test",NORMAL)));
            }
            var searchedUser = userJdbcRepository.findAll();
            assertThat(searchedUser).usingRecursiveFieldByFieldElementComparator().containsAll(users);
            assertThat(searchedUser.size()).isEqualTo(n);
        }
    }

    @Nested
    @DisplayName("FIND TEST")
    class FindUserTest {
        @Test
        @DisplayName("[findAll:성공]")
        void findAllTest() {
            var user1 = userJdbcRepository.insert(makeUser("test1", NORMAL));
            var user2 = userJdbcRepository.insert(makeUser("test2", NORMAL));
            var user3 = userJdbcRepository.insert(makeUser("test3", NORMAL));

            var users = userJdbcRepository.findAll();
            assertThat(users).usingRecursiveFieldByFieldElementComparator().contains(user1,user2,user3);
        }

        @Test
        @DisplayName("[FindById:성공] 해당하는 유저 아이디로 유저를 조회할 수 있다.")
        void findById() {
            UUID uuid1 = UUID.randomUUID();
            UUID uuid2 = UUID.randomUUID();
            UUID uuid3 = UUID.randomUUID();

            var user1= userJdbcRepository.insert(makeUserWithId(uuid1,"test1", NORMAL));
            var user2 = userJdbcRepository.insert(makeUserWithId(uuid2,"test2", BLACK));
            var user3 = userJdbcRepository.insert(makeUserWithId(uuid3,"test3", NORMAL));

            var queryUser1 = userJdbcRepository.findById(uuid1);
            var queryUser2 = userJdbcRepository.findById(uuid2);
            var queryUser3 = userJdbcRepository.findById(uuid3);

            assertThat(user1).usingRecursiveComparison().isEqualTo(queryUser1.get());
            assertThat(user2).usingRecursiveComparison().isEqualTo(queryUser2.get());
            assertThat(user3).usingRecursiveComparison().isEqualTo(queryUser3.get());
        }

        @Test
        @DisplayName("[FindAllByUserType:성공] BLACK TYPE의 유저를 찾을 수 있다.")
        void findAllByUserTypeTest() {
            var user1 = userJdbcRepository.insert(makeUser("test1", NORMAL));
            var user2 = userJdbcRepository.insert(makeUser("test2", BLACK));
            var user3 = userJdbcRepository.insert(makeUser("test3", NORMAL));
            var user4 = userJdbcRepository.insert(makeUser("test4", BLACK));

            var users = userJdbcRepository.findAllByUserType(BLACK);
            assertThat(users).usingRecursiveFieldByFieldElementComparator().contains(user2,user4);
            assertThat(users).usingRecursiveFieldByFieldElementComparator().doesNotContain(user1,user3);
        }
    }

    @Nested
    @DisplayName("DELETE TEST")
    class DeleteUserTest {
        @Test
        @DisplayName("[deleteById:성공] 유저 ID로 해당하는 유저 삭제 가능")
        void deleteByIdTest() {
            var user = makeUser("test", NORMAL);
            userJdbcRepository.insert(user);
            userJdbcRepository.deleteById(user.getId());
            var users = userJdbcRepository.findAll();
            assertThat(users).usingRecursiveFieldByFieldElementComparator().doesNotContain(user);
        }
        @Test
        @DisplayName("[deleteAll:성공] 모든 유저 삭제후 Repository 개수는 0개")
        void deleteAllTest() {
            userJdbcRepository.insert(makeUser("test", NORMAL));
            userJdbcRepository.insert(makeUser("test", NORMAL));
            userJdbcRepository.insert(makeUser("test", NORMAL));
            assertThat(userJdbcRepository.count()).isEqualTo(3);
            userJdbcRepository.deleteAll();
            assertThat(userJdbcRepository.count()).isEqualTo(0);
        }
    }
    
    @Nested
    @DisplayName("기타 테스트")
    class EtcUserTest {
        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3})
        @DisplayName("COUNT TEST")
        void countTest(int n){
            List<User> users = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                users.add(userJdbcRepository.insert(makeUser("test",NORMAL)));
            }
            assertThat(userJdbcRepository.count()).isEqualTo(n);
        }
    }

    @AfterEach
    void cleanUpAfterEach(){
        userJdbcRepository.deleteAll();
    }
    @BeforeEach
    void cleanUpBeforeEach(){
        userJdbcRepository.deleteAll();
    }

}
