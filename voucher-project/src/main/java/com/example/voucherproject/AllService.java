package com.example.voucherproject;

import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.wallet.WalletService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllService implements Runnable{

    private final Input input;
    private final UserService userService;
    private final VoucherService voucherService;
    private final WalletService walletService;

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
                case WALLET_SERVICE:
                    walletService.run();
                    break;
                case EXIT:
                    return;
                default:
                    break;
            }
        }
    }
}
