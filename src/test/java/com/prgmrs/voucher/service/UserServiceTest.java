package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UserListRequest;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.validator.OrderValidator;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserValidator userValidator;

    @Spy
    private OrderValidator orderValidator;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        // Given
        String username = "tyler";
        UserRequest userRequest = new UserRequest(username);

        // When
        UserResponse userResponse = userService.createUser(userRequest);

        // Then
        assertThat(userResponse).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findAll() {
        // Given
        UUID userUuid1 = UUID.randomUUID();
        String username1 = "tyler";
        User user1 = new User(userUuid1, username1);

        UUID userUuid2 = UUID.randomUUID();
        String username2 = "emma";
        User user2 = new User(userUuid2, username2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        given(userRepository.findAll()).willReturn(userList);

        // When
        UserListResponse userListResponse = userService.findAll();

        // Then
        List<User> retrievedUserList = userListResponse.userList();
        assertThat(retrievedUserList).isNotNull();
        assertThat(retrievedUserList).hasSize(2);
        assertThat(retrievedUserList).containsExactlyInAnyOrder(user1, user2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserListWithVoucherAssigned() {
        // Given
        UUID userUuid1 = UUID.randomUUID();
        String username1 = "tyler";
        User user1 = new User(userUuid1, username1);

        UUID userUuid2 = UUID.randomUUID();
        String username2 = "emma";
        User user2 = new User(userUuid2, username2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        given(userRepository.getUserListWithVoucherAssigned()).willReturn(userList);

        // When
        UserListResponse userListResponse = userService.getUserListWithVoucherAssigned();

        // Then
        List<User> retrievedUserList = userListResponse.userList();
        assertThat(retrievedUserList).isNotNull();
        assertThat(retrievedUserList).hasSize(2);
        assertThat(retrievedUserList).containsExactlyInAnyOrder(user1, user2);
        verify(userRepository, times(1)).getUserListWithVoucherAssigned();
    }

    @Test
    void getUserByVoucherId() {
        // Given
        UUID voucherUuid = UUID.randomUUID();
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy discountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher = new Voucher(voucherUuid, discountStrategy);

        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);
        UserListRequest userListRequest = new UserListRequest("1", Arrays.asList(voucher));
        given(userRepository.getUserByVoucherId(voucher)).willReturn(user);

        // When
        UserResponse userResponse = userService.getUserByVoucherId(userListRequest);

        // Then
        User retrievedUser = userResponse.user();
        assertThat(retrievedUser.userId()).isEqualTo(user.userId());
        assertThat(retrievedUser.username()).isEqualTo(user.username());
        verify(userRepository, times(1)).getUserByVoucherId(voucher);
    }
}
