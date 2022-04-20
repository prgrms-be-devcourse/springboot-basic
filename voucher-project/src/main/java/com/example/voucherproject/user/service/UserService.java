package com.example.voucherproject.user;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.user.repository.UserRepository;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import static com.example.voucherproject.common.enums.ServiceType.USER_SERVICE;
import static com.example.voucherproject.user.domain.UserFactory.create;

@RequiredArgsConstructor
public class UserService implements Runnable{

    private final Input input;
    private final Output output;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    public void run() {
        while(true){
            switch(input.selectUserMenu(USER_SERVICE)){
                case CREATE:{
                    var user = create(input.userName(), input.isBlacklist());
                    output.createUser(userRepository.insert(user));
                    break;
                }
                case LIST:
                    output.printUsers(userRepository.findHavingTypeAll(input.userType()));
                    break;
                case VOUCHERS:
                    checkUserVouchers();
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

    private void checkUserVouchers() {
        var users = userRepository.findAll();
        output.printUsers(users);
        var user = users.get(input.selectUser(users.size()));

        var wallets = walletRepository.findByUserId(user.getId());
        output.printWalletVouchers(wallets);
    }
}
