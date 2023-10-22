package com.weeklyMission.config;

import com.weeklyMission.client.Client;
import com.weeklyMission.console.ConsoleIO;
import com.weeklyMission.exception.ExceptionHandler;
import com.weeklyMission.member.controller.MemberController;
import com.weeklyMission.voucher.controller.VoucherController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Client client(ConsoleIO consoleIOHandler, MemberController memberController, VoucherController voucherController){
        Client client = new Client(consoleIOHandler, memberController, voucherController);

        return new ExceptionHandler(client);
    }
}
