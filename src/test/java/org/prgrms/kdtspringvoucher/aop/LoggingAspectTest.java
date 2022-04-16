package org.prgrms.kdtspringvoucher.aop;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringvoucher.customer.entity.JDBCCustomer;
import org.prgrms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringvoucher.voucher.service.FixedAmountVoucher;
import org.prgrms.kdtspringvoucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringJUnitConfig
class LoggingAspectTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspectTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdtspringvoucher.voucher", "org.prgrms.kdtspringvoucher.aop"}
    )

    // aop 를 적용하기 위해 아래의 어노테이션 필요.
    @EnableAspectJAutoProxy
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherRepository voucherRepository;
//
//    @Autowired
//    VoucherService voucherService;
// 빈으로 등록 되지 않았으면 aop가 적용되지 않는다.
    @Test
    @DisplayName("Aop teset")
    void testAop() {
        logger.info("--start test aop");
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        try {
            voucherRepository.save(fixedAmountVoucher);
        } catch (Exception e) {}


        logger.info("--end test aop");

    }

}