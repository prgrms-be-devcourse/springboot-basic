package com.example.voucher_manager;

import com.example.voucher_manager.application.RunApplication;
import com.example.voucher_manager.domain.repository.*;
import com.example.voucher_manager.domain.service.CustomerService;
import com.example.voucher_manager.domain.service.VoucherService;
import com.example.voucher_manager.io.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
//@ComponentScan(basePackages = "com.example.voucher_manager")
public class AppConfig {
//
//    @Bean
//    public CustomerRepository csvBlacklistCustomerRepository() {
//        return new CsvBlacklistCustomerRepository();
//    }
//
//    @Bean
//    public CustomerService customerService() {
//        return new CustomerService(csvBlacklistCustomerRepository());
//    }
//
//    @Bean
//    public Input consoleInput() {
//        return new ConsoleInput();
//    }
//
//    @Bean
//    public Output consoleOutput() {
//        return new ConsoleOutput();
//    }
//
//    @Bean
//    public InputValidator inputValidator(){
//        return new InputValidator();
//    }
//
//    @Bean
//    public VoucherRepository memoryVoucherRepository() {
//        return new MemoryVoucherRepository();
//    }
//
//    @Bean
//    @Profile("dev")
//    public VoucherRepository fileVoucherRepository() {
//        return new FileVoucherRepository();
//    }
//
//    @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository) {
//        return new VoucherService(voucherRepository);
//    }
//
//    @Bean
//    public CommandOperator commandOperator(VoucherService voucherService, CustomerService customerService) {
//        return new CommandOperator(voucherService, customerService);
//    }
//
//    @Bean
//    public Console console(Input input, Output output, InputValidator inputValidator, CommandOperator commandOperator) {
//        return new Console(input, output, inputValidator, commandOperator);
//    }
//
//    @Bean
//    public RunApplication runApplication(Console console) {
//        return new RunApplication(console);
//    }
}
