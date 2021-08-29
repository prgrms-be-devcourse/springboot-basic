package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

//VoucherService, OrderService, OrderRepository, VoucherRepository 의 생성에 대한 책임을 가지게 된다.
//IoC 컨테이너 역할을 하기때문에 객체들의 의존관계 설정, 객체 생성, 파괴를 담당한다.


//@PropertySource("application.properties")
//@PropertySource("application.yml") -> Spring은 PropertySource는 yaml 지원안함. factory 직접구현해야함.(SpringBoot는 지원 함)
@Configuration
@ComponentScan
@PropertySource(value = {"application.yml", "application-dev.yml", "application-prod.yml"}, factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt")
                .username("root")
                .password("root1234!")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}