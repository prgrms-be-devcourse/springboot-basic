package org.prgrms.dev.config;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.dev.voucher",
                "org.prgrms.dev.customer"}
)
@EnableConfigurationProperties
public class DBConfig implements TransactionManagementConfigurer {
    private static EmbeddedMysql embeddedMysql;

    private static final String URL = "jdbc:mysql://localhost:2215/";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test1234!";
    private static final String SCHEMA = "test_springboot_order";

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .url(URL + SCHEMA)
                .username(USERNAME)
                .password(PASSWORD)
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(1000);
        dataSource.setMinimumIdle(100);
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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

    public static EmbeddedMysql dbSetup() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser(USERNAME, PASSWORD)
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema(SCHEMA, classPathScript("schema.sql"), classPathScript("data.sql"))
                .start();

        return embeddedMysql;
    }
}
