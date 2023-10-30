package team.marco.voucher_management_system.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import team.marco.voucher_management_system.repository.JdbcVoucherRepository;

@TestConfiguration
public class TestJdbcConfiguration {
    @Bean
    public DataSource dataSource(TestDataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .driverClassName(dataSourceProperties.driver_class_name())
                .url(dataSourceProperties.url())
                .username(dataSourceProperties.userName())
                .password(dataSourceProperties.password())
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public JdbcVoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcVoucherRepository(namedParameterJdbcTemplate);
    }
}
