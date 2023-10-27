package org.programmers.springorder.config.memory;

import com.zaxxer.hikari.HikariDataSource;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.customer.repository.JdbcCustomerRepository;
import org.programmers.springorder.customer.service.CustomerService;
import org.programmers.springorder.voucher.repository.MemoryVoucherRepository;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MemoryConfig {

    @Bean
    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test_voucher")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(15);
        dataSource.setMinimumIdle(10);
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
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcCustomerRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public CustomerService customerService( CustomerRepository customerRepository, VoucherRepository voucherRepository){
        return new CustomerService(customerRepository, voucherRepository);
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository){
        return new VoucherService(voucherRepository, customerRepository);
    }


}
