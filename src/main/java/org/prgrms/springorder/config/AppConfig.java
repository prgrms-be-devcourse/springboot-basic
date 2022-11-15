package org.prgrms.springorder.config;

import com.zaxxer.hikari.HikariDataSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.sql.DataSource;
import org.prgrms.springorder.global.Console;
import org.prgrms.springorder.global.ConsoleInput;
import org.prgrms.springorder.global.ConsoleOutput;
import org.prgrms.springorder.global.Input;
import org.prgrms.springorder.global.Output;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public DataSource dataSource(JdbcProperties jdbcProperties) {
        return DataSourceBuilder.create()
            .url(jdbcProperties.getUrl())
            .username(jdbcProperties.getUsername())
            .password(jdbcProperties.getPassword())
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(JdbcProperties jdbcProperties) {
        return new NamedParameterJdbcTemplate(dataSource(jdbcProperties));
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(
        PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

}
