package org.programmers.weekly.mission.config;

import com.zaxxer.hikari.HikariDataSource;
import org.programmers.weekly.mission.util.io.Console;
import org.programmers.weekly.mission.util.io.Input;
import org.programmers.weekly.mission.util.io.Output;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

    @Bean
    public Input input() {
        return new Console();
    }

    @Bean
    public Output output() {
        return new Console();
    }

    @Bean
    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/voucher_mgmt")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(1000);
        dataSource.setMinimumIdle(100);
        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
