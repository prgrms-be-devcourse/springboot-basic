package com.example.voucherproject.common.config;

import com.example.voucherproject.AllService;
import com.example.voucherproject.common.console.ConsoleIn;
import com.example.voucherproject.common.console.ConsoleOut;
import com.example.voucherproject.common.console.Input;
import com.example.voucherproject.common.console.Output;
import com.example.voucherproject.common.file.CSVReader;
import com.example.voucherproject.common.file.CSVWriter;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.wallet.WalletService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Value("${spring.datasource.url}") private String URL;
    @Value("${spring.datasource.username}") private String USERNAME;
    @Value("${spring.datasource.password}") private String PASSWORD;

    @Bean Input input(){
        return new ConsoleIn();
    }
    @Bean Output output(){
        return new ConsoleOut();
    }

    @Bean MyWriter myWriter(){
        return new CSVWriter();
    }
    @Bean MyReader myReader(){
        return new CSVReader();
    }
    @Bean AllService allService(Input input, UserService userService, VoucherService voucherService, WalletService walletService){
        return new AllService(input, userService, voucherService, walletService);
    }

    @Bean JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    @Bean DataSource dataSource(){
        return DataSourceBuilder.create()
                .url(URL)
                .username(USERNAME)
                .password(PASSWORD)
                .type(HikariDataSource.class)
                .build();
    }
}
