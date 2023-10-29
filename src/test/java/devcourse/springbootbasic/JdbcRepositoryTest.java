package devcourse.springbootbasic;

import devcourse.springbootbasic.repository.customer.CustomerRepository;
import devcourse.springbootbasic.repository.customer.JdbcCustomerRepository;
import devcourse.springbootbasic.repository.voucher.JdbcVoucherRepository;
import devcourse.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

@SpringJUnitConfig
@ContextConfiguration(classes = JdbcRepositoryTest.Config.class)
public abstract class JdbcRepositoryTest {

    @Configuration
    static class Config {
        @Bean
        public CustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcCustomerRepository(jdbcTemplate);
        }

        @Bean
        public VoucherRepository jdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/test_db?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&useSSL=true")
                    .username("root")
                    .password("local59!")
                    .build();
        }
    }
}
