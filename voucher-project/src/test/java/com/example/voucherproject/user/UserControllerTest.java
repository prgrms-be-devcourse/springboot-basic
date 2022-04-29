package com.example.voucherproject.user;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.voucherproject.common.Helper.UserHelper.makeUser;
import static com.example.voucherproject.user.model.UserType.BLACK;
import static com.example.voucherproject.user.model.UserType.NORMAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("유저 컨트롤러 테스트")
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Nested
    class 생성 {
        @Test
        @DisplayName("[GET /user: 성공] 유저 생성 페이지 반환")
        void addUserViewTest() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                    .andExpect(handler().handlerType(UserController.class))
                    .andExpect(handler().methodName("addUserView"))
                    .andExpect(view().name("user/new-user")).andReturn();
        }

        @Test
        @DisplayName("[POST /user: 성공] 유저 생성 후 유저 목록 페이지로 리다이렉트")
        void addUserTest() throws Exception {
            var user = makeUser("test1", NORMAL);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/user")
                            .content(objectMapper.writeValueAsString(user))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(handler().handlerType(UserController.class))
                    .andExpect(handler().methodName("addUser"));
//                    .andExpect(redirectedUrl("user/users"));
//                    .andExpect(view().name("user/users")).andReturn();
        }
    }

    @Nested
    class 조회 {
        @Test
        @DisplayName("[GET /users:성공] 모든 유저 조회")
        void getUsersViewTest() throws Exception {
            List<User> given = new ArrayList<>();
            given.add(makeUser("test1", NORMAL));
            given.add(makeUser("test2", BLACK));
            given.add(makeUser("test3", NORMAL));

            when(userService.findAll())
                    .thenReturn(given);

            mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                        .andExpect(handler().handlerType(UserController.class))
                        .andExpect(handler().methodName("getUsersView"))
                        .andExpect(model().attribute("users", given))
                        .andExpect(view().name("user/users")).andReturn();
        }

        @Test
        @DisplayName("[GET /user/{id}:성공] 유저 상세 조회 성공")
        void getUserDetailTest() throws Exception {
            var user = makeUser("test", NORMAL);

            when(userService.findById(any(UUID.class)))
                    .thenReturn(Optional.of(user));

            mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", user.getId()))
                    .andExpect(handler().handlerType(UserController.class))
                    .andExpect(handler().methodName("getUserDetail"))
//                    .andExpect(jsonPath("$.id", Matchers.is(user.getId())))
//                    .andExpect(model().attribute("user", user))
                    .andExpect(view().name("user/user-detail")).andReturn();
        }

        @Test
        @DisplayName("[GET /user/{id}:실패] 유저 상세 조회 실패 - 존재하지 않는 아이디")
        void getUserDetailFailTest() throws Exception {
            var user = makeUser("test",NORMAL);

            when(userService.findById(any(UUID.class)))
                    .thenReturn(Optional.of(user));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/user/{id}", UUID.randomUUID()))
                            .andExpect(handler().handlerType(UserController.class))
//                            .andExpect(jsonPath("$.id", Matchers.not(user.getId())))
                            .andExpect(handler().methodName("getUserDetail"));
//                            .andExpect(model().attribute("user", user))
//                            .andExpect(view().name("basic/404")).andReturn();
        }
    }

    @Nested
    class 삭제 {
        @Test
        @DisplayName("[POST /user/{id}:성공] {id}유저 삭제 후 리다이렉트")
        void deleteUserByIdTest() throws Exception {
            var user = makeUser("test", NORMAL);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/user/{id}",user.getId()))
                    .andExpect(handler().handlerType(UserController.class))
                    .andExpect(handler().methodName("deleteUserById"))
                    .andExpect(view().name("redirect:../users/")).andReturn();
        }
    }
}

