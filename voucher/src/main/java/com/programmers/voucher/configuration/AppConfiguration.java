package com.programmers.voucher.configuration;

import com.programmers.voucher.CommandLineApplication;
import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.console.TextIoConsole;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.blacklist.BlackListStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {
    @Value("${mysql.user-id}")
    private  String MYSQL_ID;

    @Value("${mysql.password}")
    private String MYSQL_PASSWORD;

    @Bean
    public Console console() {
        return new TextIoConsole();
    }

    @Bean
    public CommandLineApplication commandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory, BlackListStream blackListStream, Printer printer) {
        return new CommandLineApplication(console, voucherStream, voucherFactory, blackListStream, printer);
    }

    @Bean
    public VoucherFactory voucherFactory(VoucherStream voucherStream) {
        return new VoucherFactory(voucherStream);
    }
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3308/customer")
                .username(MYSQL_ID)
                .password(MYSQL_PASSWORD)
                .type(HikariDataSource.class)
                .build();
//            dataSource.setMaximumPoolSize(1000);    // Pool의 최대 Connection 생성 개수
//            dataSource.setMinimumIdle(100);         // Connection 생성 개수
        return dataSource;
    }

}
