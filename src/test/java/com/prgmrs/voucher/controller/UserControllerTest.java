package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.UserListRequest;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import com.prgmrs.voucher.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("유저 컨트롤러 레이어를 테스트한다.")
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저 생성 요청을 보낸다.")
    void CreateUser_UserRequest_SameUserResponse() {
        // Given
        User user = new User(UUID.randomUUID(), "tyler");
        UserRequest userRequest = new UserRequest(user.username());
        UserResponse userResponse = new UserResponse(user);
        given(userService.createUser(userRequest)).willReturn(userResponse);

        // When
        UserResponse response = userController.createUser(userRequest);

        // Then
        assertEquals(userResponse, response);
    }

    @Test
    @DisplayName("모든 유저를 불러오는 것을 요청한다.")
    void GetAllUsers_NoParam_SameUserListResponse() {
        // Given
        User user1 = new User(UUID.randomUUID(), "tyler");
        User user2 = new User(UUID.randomUUID(), "john");
        UserListResponse userListResponse = new UserListResponse(Arrays.asList(user1, user2));
        given(userService.findAll()).willReturn(userListResponse);

        // When
        UserListResponse response = userController.getAllUsers();

        // Then
        assertEquals(userListResponse, response);
    }

    @Test
    @DisplayName("바우처를 핟당 받은 유저들을 불러온다.")
    void GetUserListWithVoucherAssigned_NoParam_SameUserListResponse() {
        // Given
        User user1 = new User(UUID.randomUUID(), "tyler");
        User user2 = new User(UUID.randomUUID(), "john");
        UserListResponse userListResponse = new UserListResponse(Arrays.asList(user1, user2));
        given(userService.getUserListWithVoucherAssigned()).willReturn(userListResponse);

        // When
        UserListResponse response = userController.getUserListWithVoucherAssigned();

        // Then
        assertEquals(userListResponse, response);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 유저를 불러온다.")
    void GetUserByVoucherId_userListRequest_SameUserResponse() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(uuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(uuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        String order = "1";
        UserListRequest userListRequest = new UserListRequest(order, voucherList);
        UUID uuid3 = UUID.randomUUID();
        User user = new User(uuid3, "tyler");
        UserResponse userResponse = new UserResponse(user);
        given(userService.getUserByVoucherId(userListRequest)).willReturn(userResponse);


        // When
        UserResponse response = userController.getUserByVoucherId(userListRequest);

        // Then
        assertEquals(userResponse, response);
    }
}