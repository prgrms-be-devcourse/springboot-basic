package team.marco.voucher_management_system.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    private final String username;
    private final String password;
    private final String driverClassName;
    private final String url;

    public JdbcTemplateConfig(@Value("${spring.datasource.username}") String username,
                              @Value("${spring.datasource.password}") String password,
                              @Value("${spring.datasource.driver-class-name}") String driverClassName,
                              @Value("${spring.datasource.url}") String url) {
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.url = url;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
