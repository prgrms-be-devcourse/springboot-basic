package com.programmers.commandline.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyDataSource {

    private final String url;
    private final String user;
    private final String password;

    public MyDataSource(@Value("${db.url}") String url, @Value("${db.user}") String user, @Value("${db.password}") String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public javax.sql.DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url(this.url)
                .username(this.user)
                .password(this.password)
                .type(HikariDataSource.class)
                .build();
    }
}
