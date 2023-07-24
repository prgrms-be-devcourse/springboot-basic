package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.controller.console.UserController;
import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("유저 컨트롤러 레이어를 테스트한다.")
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;
    private UserListResponse userListResponse;
    private VoucherIdRequest voucherIdRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID uuidTyler = UUID.randomUUID();
        User userTyler = new User(uuidTyler, new Username("tyler"));
        userRequest = new UserRequest(userTyler.username().value());
        userResponse = new UserResponse(uuidTyler, userTyler.username().value());


        UUID uuidJohn = UUID.randomUUID();
        User userJohn = new User(uuidJohn, new Username("john"));
        userListResponse = new UserListResponse(Arrays.asList(userTyler, userJohn));
    }

    @Test
    @DisplayName("유저 생성 요청을 보낸다.")
    void CreateUser_UserRequest_SameUserResponse() {
        // Given
        given(userService.createUser(userRequest)).willReturn(userResponse);

        // When
        ResponseDTO<?> responseDTO = userController.createUser(userRequest);

        // Then
        assertEquals(userResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("모든 유저를 불러오는 것을 요청한다.")
    void GetAllUsers_NoParam_SameUserListResponse() {
        // Given
        given(userService.findAll()).willReturn(userListResponse);

        // When
        ResponseDTO<?> responseDTO = userController.getAllUsers();

        // Then
        assertEquals(userListResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("바우처를 핟당 받은 유저들을 불러온다.")
    void GetUserListWithVoucherAssigned_NoParam_SameUserListResponse() {
        // Given
        given(userService.getUserListWithVoucherAssigned()).willReturn(userListResponse);

        // When
        ResponseDTO<?> responseDTO = userController.getUserListWithVoucherAssigned();

        // Then
        assertEquals(userListResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 유저를 불러온다.")
    void GetUserByVoucherId_userListRequest_SameUserResponse() {
        // Given
        UUID voucherUuid = UUID.randomUUID();
        voucherIdRequest = new VoucherIdRequest(voucherUuid.toString());
        given(userService.getUserByVoucherId(voucherIdRequest)).willReturn(userResponse);

        // When
        ResponseDTO<?> responseDTO = userController.getUserByVoucherId(voucherIdRequest);

        // Then
        assertEquals(userResponse, responseDTO.getData());
    }
}