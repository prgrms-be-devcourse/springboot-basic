package com.example.voucherproject.user;

import com.example.voucherproject.user.dto.UserDtoMapper;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.user.service.UserWebService;
import com.example.voucherproject.wallet.model.Wallet;
import com.example.voucherproject.wallet.repository.WalletRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.voucherproject.common.Helper.UserHelper.makeUser;
import static com.example.voucherproject.common.Helper.WalletHelper.makeWallet;
import static com.example.voucherproject.user.model.UserType.BLACK;
import static com.example.voucherproject.user.model.UserType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("유저 서비스 테스트")
public class UserServiceTest {

    @Mock(name = "userRepository")
    private UserRepository userRepository;

    @Mock(name = "walletRepository")
    private WalletRepository walletRepository;

    @InjectMocks
    private UserWebService userService;

    @Test
    @DisplayName("[createUser:성공] 유저를 생성할 수 있다. (행위 검증, 상태 검증)")
    void createUserTest() {
        var givenUser = makeUser("test0", BLACK);

        // Mock 객체에서 기대되는 행위
        when(userRepository.insert(any(User.class)))
                .thenReturn(givenUser);

        var returnUser = userService.createUser(givenUser);
        then(userRepository).should().insert(givenUser);

        // 상태 검증?
        assertThat(returnUser).isInstanceOf(User.class);
        assertThat(returnUser.getId()).isEqualTo(givenUser.getId());
        assertThat(returnUser.getName()).isEqualTo(givenUser.getName());
        assertThat(returnUser.getType()).isEqualTo(givenUser.getType());
    }

    @Test
    @DisplayName("[findAll:성공] 전체 유저 조회 (행위 검증, 상태검증)")
    void findAllTest(){
        List<User> givenUsers = new ArrayList<>();
        givenUsers.add(makeUser("test1", BLACK));
        givenUsers.add(makeUser("test2", NORMAL));
        givenUsers.add(makeUser("test3", NORMAL));

        // Mock 객체에서 기대되는 행위
        when(userRepository.findAll())
                .thenReturn(givenUsers);

        var serviceUsers = userService.findAll();
        then(userRepository).should(times(1)).findAll();

        // 상태 검증
        assertThat(serviceUsers).usingRecursiveFieldByFieldElementComparator()
                .containsAll(givenUsers);
    }

    @Test
    @DisplayName("[findById:성공] 유저 ID로 해당 유저를 조회할 수 있다.(행위검증, 상태검증)")
    void findById(){
        var givenUser = makeUser("test", BLACK);

        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.ofNullable(givenUser));

        var returnUserDTO = userService.findById(givenUser.getId());
        then(userRepository).should(times(1)).findById(givenUser.getId());

        // 상태 검증
        assertThat(returnUserDTO).isNotEmpty();

        var userDTO = returnUserDTO.get();
        assertThat(userDTO.getName()).isEqualTo(givenUser.getName());
        assertThat(userDTO.getType()).isEqualTo(givenUser.getType());
        assertThat(userDTO.getId()).isEqualTo(givenUser.getId());
    }

    @Test
    @DisplayName("[deleteById:성공] 특정 유저 삭제 (행위 검증)")
    void deleteById(){

        var givenUser = makeUser("test5", BLACK);
        List<Wallet> givenWallets = List.of(makeWallet(), makeWallet(),makeWallet());

        // Mock 객체에서 기대되는 행위
        when(userRepository.deleteById(any(UUID.class)))
                .thenReturn(1);

        when(walletRepository.findAllByUserId(any(UUID.class)))
                .thenReturn(givenWallets);

        when(walletRepository.deleteById(any(UUID.class)))
                .thenReturn(3);

        //userService에서 deleteById가 호출되면
        var returnValue = userService.deleteById(givenUser.getId());
        assertThat(returnValue).isTrue();

        // 다음의 세가지 메서드가 호출되어야 한다.
        then(userRepository).should().deleteById(any(UUID.class));
        then(walletRepository).should().findAllByUserId(any(UUID.class));
        then(walletRepository).should(times(3)).deleteById(any(UUID.class));
    }
}
