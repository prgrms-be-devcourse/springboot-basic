package com.voucher.vouchermanagement.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

public class ApplicationConfig {
/*    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
    *//*DataSource dataSource = DataSourceBuilder.create()
        .url("jdbc:mysql://localhost/order_mgmt")
        .username("root")
        .password("root1234!")
        .type(HikariDataSource.class)
        .build();*//*
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .build();
    }*/
}
