package org.prgrms.springorder.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.sql.DataSource;
import org.prgrms.springorder.console.io.Console;
import org.prgrms.springorder.console.io.ConsoleInput;
import org.prgrms.springorder.console.io.ConsoleOutput;
import org.prgrms.springorder.console.io.Input;
import org.prgrms.springorder.console.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class AppConfig {

    @Bean
    public Input consoleInput() {
        return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    @Bean
    public Console console() {
        return new Console(consoleInput(), consoleOutput());
    }

    @Bean
    @Profile({"dev"})
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile({"dev"})
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Profile({"dev"})
    public TransactionTemplate transactionTemplate(
        PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

}
