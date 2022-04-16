package com.example.voucherproject.user.service;

import com.example.voucherproject.common.io.console.Input;
import com.example.voucherproject.common.io.console.Output;
import com.example.voucherproject.user.domain.UserFactory;
import com.example.voucherproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import static com.example.voucherproject.common.enums.ServiceType.USER_SERVICE;

@RequiredArgsConstructor
public class UserService implements Runnable{

    private final Input input;
    private final Output output;
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    @Override
    public void run() {
        while(true){
            switch(input.selectMenu(USER_SERVICE)){
                case CREATE:
                    var user = userFactory.create(input.userName(), input.isBlacklist());
                    output.createUser(userRepository.save(user));
                    break;
                case LIST:
                    output.users(userRepository.getUserList(input.userType()));
                    break;
                case HOME:
                    output.home();
                    return;
                default:
                    output.error();
                    break;
            }
        }
    }
}
