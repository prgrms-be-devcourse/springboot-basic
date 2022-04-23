package com.kdt.commandLineApp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "dev")
public class AppProperties {
    private String customer_blacklist_info;

    private String voucher_info;

    private String db_driver_class;

    private String db_url;

    private String db_user;

    private String db_pwd;
}
