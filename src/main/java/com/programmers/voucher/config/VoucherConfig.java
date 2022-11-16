package com.programmers.voucher.config;

import com.zaxxer.hikari.HikariDataSource;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
public class VoucherConfig {
    private final VoucherProperties properties;
    private final static String URL = "jdbc:mysql://localhost:3306/order_mgmt";
    private final static String USER = "kiseo";
    private final static String PW = "1234";
    @Autowired
    public VoucherConfig(VoucherProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Wini wini() throws IOException {

        File file = new File(properties.getSavePath());
        if (!file.exists()) {
            file.createNewFile();
        }

        return new Wini(file);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(URL)
                .username(USER)
                .password(PW)
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
