package org.prgrms.kdt.storage;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.TestConfig;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class JdbcVoucherStorageTest {

    @Autowired
    private JdbcVoucherStorage jdbcVoucherStorage;

    private String customerId;
    private Voucher voucher;
    private String voucherId;
    private EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setjdbc() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("voucher.sql"))
                .start();
    }

    @BeforeEach
    void setup() {
        voucherId = UUID.randomUUID().toString();
        customerId = UUID.randomUUID().toString();
        voucher = new FixedAmountVoucher(voucherId, 1000, customerId);
        jdbcVoucherStorage.save(voucher);
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("소유하지 않은 바우처를 새로 추가할 수 있다.")
    void testInsertNewNotOwnedVoucher() {
        String newVoucherId = UUID.randomUUID().toString();
        Voucher newVoucher = new PercentDiscountVoucher(newVoucherId, 40);

        jdbcVoucherStorage.save(newVoucher);
        Voucher findVoucher = jdbcVoucherStorage.findById(newVoucherId).get();

        assertThat(newVoucher).usingRecursiveComparison()
                .isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("소유한 고객이 있는 바우처를 새로 추가할 수 있다.")
    void testInsertNewOwnedVoucher() {
        String newVoucherId = UUID.randomUUID().toString();
        String newCustomerId = UUID.randomUUID().toString();
        Voucher newVoucher = new FixedAmountVoucher(newVoucherId, 3000, newCustomerId);

        jdbcVoucherStorage.save(newVoucher);
        Voucher findVoucher = jdbcVoucherStorage.findById(newVoucherId).get();

        assertThat(newVoucher).usingRecursiveComparison()
                .isEqualTo(findVoucher);

    }

    @Test
    @DisplayName("생성된 바우처를 모두 조회할 수 있다.")
    void testFindAllVoucher() {
        List<Voucher> voucherList = jdbcVoucherStorage.findAll();

        assertFalse(voucherList.isEmpty());
    }

    @Test
    @DisplayName("바우처 ID를 사용하여 특정 바우처를 삭제할 수 있다.")
    void testDeleteVoucher() {
        String deleteVoucherId = UUID.randomUUID().toString();
        Voucher deleteVoucher = new PercentDiscountVoucher(deleteVoucherId, 50, UUID.randomUUID().toString());
        jdbcVoucherStorage.save(deleteVoucher);

        jdbcVoucherStorage.deleteById(deleteVoucherId);

        assertEquals(Optional.empty(), jdbcVoucherStorage.findById(deleteVoucherId));
    }

    @Test
    @DisplayName("바우처 ID를 사용하여 특정 바우처를 찾을 수 있다.")
    void testFindVoucherById() {
        Voucher findVoucher = jdbcVoucherStorage.findById(voucherId).get();

        assertThat(voucher).usingRecursiveComparison()
                .isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("바우처 ID를 통하여 특정 바우처를 가진 고객 아이디 정보 가져올 수 있다.")
    void testCustomerIdBySpecificVoucher() {
        Voucher findVoucher = jdbcVoucherStorage.findById(voucherId).get();

        String findCustomerId = findVoucher.getOwnerId().orElse(null);

        assertEquals(customerId, findCustomerId);
    }

}
