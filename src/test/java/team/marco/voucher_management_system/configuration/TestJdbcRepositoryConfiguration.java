package team.marco.voucher_management_system.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import team.marco.voucher_management_system.repository.JdbcCustomerRepository;
import team.marco.voucher_management_system.repository.JdbcVoucherRepository;
import team.marco.voucher_management_system.repository.JdbcWalletRepository;

@TestConfiguration
@ComponentScan(basePackageClasses = TestJdbcConfiguration.class)
public class TestJdbcRepositoryConfiguration {
    @Bean
    public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcVoucherRepository(jdbcTemplate);
    }

    @Bean
    public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcCustomerRepository(jdbcTemplate);
    }

    @Bean
    public JdbcWalletRepository jdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcWalletRepository(jdbcTemplate);
    }
}
