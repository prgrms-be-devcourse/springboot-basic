package com.example.voucherproject;

import com.example.voucherproject.common.io.Input;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllService implements Runnable{

    private final Input input;
    private final UserService userService;
    private final VoucherService voucherService;

    @Override
    public void run() {
        while(true){
            switch(input.selectService()){
                case USER_SERVICE:
                    userService.run();
                    break;
                case VOUCHER_SERVICE:
                    voucherService.run();
                    break;
                case EXIT:
                    return;
                default:
                    break;
            }
        }
    }
}
