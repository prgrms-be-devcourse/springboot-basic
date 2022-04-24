package com.programmers.springbootbasic.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.programmers.springbootbasic.configuration",
                                "com.programmers.springbootbasic.repository",
                                "com.programmers.springbootbasic.service"})
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
public class RootAppConfig {

    private final DataSourceProperties dataSourceProperties;

    @Autowired
    public RootAppConfig(DataSourceProperties dataSourceInfoProvider) {
        this.dataSourceProperties = dataSourceInfoProvider;
    }

    @Bean
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .type(HikariDataSource.class)
                .build();
    }

}
