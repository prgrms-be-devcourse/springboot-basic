package org.prgrms.kdt.common;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 10:47 오후
 */

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdt.customer", "org.prgrms.kdt.voucher", "org.prgrms.kdt.wallet", "org.prgrms.kdt.validate"}
)
public class JdbcDataConfig {

    @Bean
    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();

//            dataSource.setMinimumIdle(100);
//            dataSource.setMaximumPoolSize(1000);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
