package com.example.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConstructorBinding
@ConfigurationProperties(prefix = "dev")
@Profile("dev")
public class JdbcProperties {

    private final String url;
    private final String username;
    private final String password;

    public JdbcProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
