package org.programmers.program.voucher.service;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.annotation.Testable;
import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;
import org.programmers.program.voucher.repository.MemoryVoucherRepository;
import org.programmers.program.voucher.repository.NamedJdbcVoucherRepository;
import org.programmers.program.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig
// @SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {
    @Configuration
    @ComponentScan(basePackages = "org.programmers.program.voucher")
    static class Config{
        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/vouchers")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("vouchers", classPathScript("voucherSchema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp(){
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처 생성 테스트")
    void createTest(){
        var id = UUID.randomUUID();
        var v1 = voucherService.createVoucher(VoucherType.PERCENT, id, 40L);

        assertThat(voucherService.count()).isOne();
        assertThat(voucherService.findById(id).get().getVoucherId()).isEqualTo(id);
    }
}