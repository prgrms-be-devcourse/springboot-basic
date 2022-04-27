package org.prgrms.springbootbasic;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.springbootbasic.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
@ComponentScan
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class CommandLineAppConfiguration {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt")
                .username("root")
                .password("1q2w3e4r!")
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
}
