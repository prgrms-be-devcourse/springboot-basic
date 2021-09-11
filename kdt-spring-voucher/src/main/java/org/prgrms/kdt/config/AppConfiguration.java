package org.prgrms.kdt.config;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.controller.VoucherController;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@ComponentScan(basePackages = {
//        "org.prgrms.kdt.domain",
//        "org.prgrms.kdt.utill",
//})
@Configuration
@EnableTransactionManagement
public class AppConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean
    public DataSource dataSource(){
        var datasource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/voucher_order_mgmt")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class) //쓸 구현체를 넣어준다 .
                .build();

        datasource.setMaximumPoolSize(100);
        datasource.setMinimumIdle(100);
        return datasource;
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
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


}
