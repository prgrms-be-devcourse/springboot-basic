package org.prgrms.vouchermanager;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringJUnitConfig
@Transactional
public abstract class TestConfig {
    @TestConfiguration
    static class DataSourceConfig {

        @Bean
        public DataSource dataSource() {
            DataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1")
                    .driverClassName("org.h2.Driver")
                    .username("sa")
                    .password("")
                    .type(HikariDataSource.class)
                    .build();

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Object SqlFixture;
            jdbcTemplate.execute("create table if not exists voucher(\n" +
                    "                        id BINARY(16) not null primary key ,\n" +
                    "                        amount int not null,\n" +
                    "                        voucher_type varchar(25) not null\n" +
                    ");\n" +
                    "\n" +
                    "create table if not exists customer(\n" +
                    "                         id BINARY(16) not null ,\n" +
                    "                         name varchar(25) not null ,\n" +
                    "                         email varchar(25) unique not null,\n" +
                    "                         isBlack boolean not null\n" +
                    ");\n" +
                    "create table if not exists wallet(\n" +
                    "                       id int not null primary key auto_increment,\n" +
                    "                       customer_email varchar(25) not null ,\n" +
                    "                       voucher_id BINARY(16) not null ,\n" +
                    "                       foreign key (customer_email) references customer(email),\n" +
                    "                       foreign key (voucher_id) references voucher(id)\n" +
                    ");");

            return dataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }
}

