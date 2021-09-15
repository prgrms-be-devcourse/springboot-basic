package org.prgrms.kdt.engine.voucher.controller;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.junit.jupiter.api.Assertions.*;

class VoucherRestControllerTest {
    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    void testGetVouchers() {

    }

    @Test
    void createVoucher() {
    }

    @Test
    void findVoucher() {
    }

    @Test
    void deleteVoucher() {
    }

    @Test
    void getVouchersByType() {
    }

    @Test
    void getVouchersByCreatedDate() {
    }
}
