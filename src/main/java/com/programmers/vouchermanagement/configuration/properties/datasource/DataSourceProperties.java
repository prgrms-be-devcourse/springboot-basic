package com.programmers.vouchermanagement.configuration.properties.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Conditional;

import com.programmers.vouchermanagement.configuration.profiles.DBEnabledCondition;

@Conditional(DBEnabledCondition.class)
@ConfigurationProperties("datasource")
public class DataSourceProperties {
    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;

    @ConstructorBinding
    public DataSourceProperties(String driverClassName, String url, String username, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
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
