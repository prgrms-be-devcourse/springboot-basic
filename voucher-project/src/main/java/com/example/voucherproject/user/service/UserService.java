package com.example.voucherproject.user.service;

import com.example.voucherproject.common.io.Input;
import com.example.voucherproject.common.io.Output;
import com.example.voucherproject.user.domain.UserFactory;
import com.example.voucherproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.voucherproject.common.enums.ServiceType.USER_SERVICE;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements Runnable{

    private final Input input;
    private final Output output;
    private final UserFactory userFactory;        // User 생성
    private final UserRepository userRepository;  // User 저장

    @Override
    public void run() {
        log.info("User service start");
        while(true){
            switch(input.selectMenu(USER_SERVICE)){
                case CREATE:
                    log.info("select create");
                    var user = userFactory.create(input.userName(), input.isBlacklist());
                    output.createUser(userRepository.save(user));
                    break;
                case LIST:
                    log.info("select list");
                    output.users(userRepository.getUserList(input.userType()));
                    break;
                case HOME:
                    log.info("select exit");
                    output.exit();
                    log.info("voucher service finished");
                    return;
                default:
                    log.info("select error");
                    output.error();
                    break;
            }
        }
    }
}
