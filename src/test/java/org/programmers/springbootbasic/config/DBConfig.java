package org.programmers.springbootbasic.config;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;

@Configuration
@ComponentScan(
        basePackages = {"org.programmers.springbootbasic.customer", "org.programmers.springbootbasic.voucher", "org.programmers.springbootbasic.wallet"}
)
@EnableConfigurationProperties
public class DBConfig {

    private static final String URL = "jdbc:mysql://localhost:2215/test-order_mgmt";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test1234!";
    private static final String SCHEMA = "test-order_mgmt";

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(URL)
                .username(USERNAME)
                .password(PASSWORD)
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

    public static EmbeddedMysql dbSetup() {
        MysqldConfig mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser(USERNAME, PASSWORD)
                .withTimeZone("Asia/Seoul")
                .build();

        return anEmbeddedMysql(mysqldConfig)
                .addSchema(SCHEMA, classPathScript("schema.sql"))
                .start();
    }
}
