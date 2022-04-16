package com.example.voucherproject.common.config;

import com.example.voucherproject.AllService;
import com.example.voucherproject.common.file.CSVReader;
import com.example.voucherproject.common.file.CSVWriter;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.common.io.ConsoleIn;
import com.example.voucherproject.common.io.ConsoleOut;
import com.example.voucherproject.common.io.Input;
import com.example.voucherproject.common.io.Output;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Input input(){
        return new ConsoleIn();
    }
    @Bean
    public Output output(){
        return new ConsoleOut();
    }

    @Bean
    public MyWriter myWriter(){
        return new CSVWriter();
    }
    @Bean
    public MyReader myReader(){
        return new CSVReader();
    }
    @Bean
    public AllService allService(Input input, UserService userService, VoucherService voucherService){
        return new AllService(input, userService, voucherService);
    }
}
