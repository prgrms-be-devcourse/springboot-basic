package com.prgrms.springbootbasic.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;

@Profile("dev")
@Configuration
@ComponentScan(basePackages = {
        "com.prgrms.springbootbasic.app",
        "com.prgrms.springbootbasic.console",
        "com.prgrms.springbootbasic.voucher",
        "com.prgrms.springbootbasic.customer"
})
@PropertySource(value = "application.yml")
public class AppConfiguration {

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/springboot_basic?serverTimezone=UTC&autoReconnect=true&verifyServerCertificate=false&useSSL=true");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("cjsak123");
        config.isReadOnly();
        return new HikariDataSource(config);
    }
}
