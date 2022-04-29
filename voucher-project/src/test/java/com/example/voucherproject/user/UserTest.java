package com.example.voucherproject.user;


import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.example.voucherproject.common.Helper.UserHelper.makeUser;
import static com.example.voucherproject.common.Helper.UserHelper.makeUserDTO;
import static com.example.voucherproject.user.dto.UserDtoMapper.asUserDTO;
import static com.example.voucherproject.user.dto.UserDtoMapper.asUserModel;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Nested
    @DisplayName("유저 생성 테스트")
    class UserCreateTest {

        @ParameterizedTest
        @EnumSource(value = UserType.class, names= {"NORMAL", "BLACK"})
        @DisplayName("[create:성공] 각 타입의 유저를 생성할 수 있다.")
        void createUserTypeTest(UserType type){
            var normalUser = makeUser("test", type);
            assertThat(normalUser).isInstanceOf(User.class);
            assertThat(normalUser.getType()).isEqualTo(type);
        }

    }
    @Nested
    @DisplayName("유저 DTO 변환 테스트")
    class UserDTOCreateTest {

        @ParameterizedTest
        @EnumSource(value = UserType.class, names= {"NORMAL", "BLACK"})
        @DisplayName("[asUserModel:성공] 유저 DTO → 유저 Model 변환할 수 있다.")
        void asUserModelTest(UserType type){
            var userDTO = makeUserDTO("test",type);
            var user = asUserModel(userDTO);

            assertThat(user).isInstanceOf(User.class);
            assertThat(user.getName()).isEqualTo(userDTO.getName());
            assertThat(user.getType()).isEqualTo(userDTO.getType());
        }
        
        @ParameterizedTest
        @EnumSource(value = UserType.class, names= {"NORMAL", "BLACK"})
        @DisplayName("[asUserDTO:성공] 유저 Model → 유저 DTO 변환할 수 있다.")
        void asUserDTOTest(UserType type){
            var user = makeUser("test", type);
            var dto = asUserDTO(user);

            assertThat(dto).isInstanceOf(UserDTO.Result.class);
            assertThat(dto.getName()).isEqualTo(user.getName());
            assertThat(dto.getType()).isEqualTo(user.getType());
        }
    }

}
