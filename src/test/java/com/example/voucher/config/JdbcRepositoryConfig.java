package com.example.voucher.config;

import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.example.voucher.repository.JdbcVoucherRepository;

@TestConfiguration
public class JdbcRepositoryConfig {

    @Bean
    public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcVoucherRepository(jdbcTemplate);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/kdt");
        dataSource.setUsername("study");
        dataSource.setPassword("study");
        return dataSource;
    }

}
