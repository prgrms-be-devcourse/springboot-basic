package com.weeklyMission.config;

import com.weeklyMission.client.Client;
import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.exception.ExceptionHandler;
import com.weeklyMission.member.controller.MemberController;
import com.weeklyMission.voucher.controller.VoucherController;
import com.weeklyMission.wallet.controller.WalletController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Client client(ConsoleIO consoleIOHandler, MemberController memberController, VoucherController voucherController, WalletController walletController){
        Client client = new Client(consoleIOHandler, memberController, voucherController,
            walletController);

        return new ExceptionHandler(client);
    }
}
