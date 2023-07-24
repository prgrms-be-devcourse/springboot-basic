package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("유저 서비스 레이어를 테스트한다.")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User userTyler;
    private User userEmma;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID userUuidTyler = UUID.randomUUID();
        Username usernameTyler = new Username("tyler");
        userTyler = new User(userUuidTyler, usernameTyler);

        UUID userUuidEmma = UUID.randomUUID();
        Username usernameEmma = new Username("emma");
        userEmma = new User(userUuidEmma, usernameEmma);
    }

    @Test
    @DisplayName("유저 생성을 테스트한다.")
    void CreateUser_UserRequest_UserResponseSameAsGivenUser() {
        // Given
        UserRequest userRequest = new UserRequest("james");

        // When
        UserResponse userResponse = userService.createUser(userRequest);

        // Then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertThat(userResponse).isNotNull();
        assertThat(capturedUser.userId()).isNotNull();
        assertThat(capturedUser.username().value()).isEqualTo("james");
    }

    @Test
    @DisplayName("모든 유저를 찾는 조회를 테스트한다.")
    void FindAll_NoParam_UserListResopnseSameAsGivenUsers() {
        // Given
        given(userRepository.findAll()).willReturn(Arrays.asList(userTyler, userEmma));

        // When
        UserListResponse userListResponse = userService.findAll();

        // Then
        List<User> retrievedUserList = userListResponse.userList();
        assertThat(retrievedUserList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(userTyler, userEmma);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("할당된 바우처가 있는 유저 리스트 조회를 테스트한다.")
    void GetUserListWithVoucherAssigned_NoParam_UserListResponseSameAsGivenUsers() {
        // Given
        given(userRepository.getUserListWithVoucherAssigned()).willReturn(Arrays.asList(userTyler, userEmma));

        // When
        UserListResponse userListResponse = userService.getUserListWithVoucherAssigned();

        // Then
        List<User> retrievedUserList = userListResponse.userList();
        assertThat(retrievedUserList).isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(userTyler, userEmma);
        verify(userRepository, times(1)).getUserListWithVoucherAssigned();
    }

    @Test
    @DisplayName("바우처 번호에 해당하는 유저를 조회한다.")
    void GetUserByVoucherId_UserListRequest_UserResponseSameAsGivenUser() {
        // Given
        UUID voucherUUID = UUID.randomUUID();
        VoucherIdRequest voucherIdRequest = new VoucherIdRequest(voucherUUID.toString());
        given(userRepository.getUserByVoucherId(voucherUUID)).willReturn(userTyler);

        // When
        UserResponse userResponse = userService.getUserByVoucherId(voucherIdRequest);

        // Then
        assertThat(userResponse.uuid()).isEqualTo(userTyler.userId());
        assertThat(userResponse.username()).isEqualTo(userTyler.username().value());
        verify(userRepository, times(1)).getUserByVoucherId(voucherUUID);
    }
}
