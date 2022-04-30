package org.prgrms.spring_week1.Voucher.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.prgrms.spring_week1.Voucher.model.FixedAmountVoucher;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherStatus;
import org.prgrms.spring_week1.customer.model.Customer;
import org.prgrms.spring_week1.customer.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepositoryTest.class);

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup(){
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-week1", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private static final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, UUID.randomUUID());

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @Order(1)
    @DisplayName("바우처를 생성할 수 있다")
    void insertTest() {
        voucherRepository.insert(voucher);
        List<Voucher> vouchers = voucherRepository.getAllVoucher();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 id로 검색할 수 있다")
    void findByIdTest() {
        Optional<Voucher> findVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(findVoucher.isEmpty(), is(false));


    }

    @Test
    @Order(3)
    @DisplayName("바우처를 수정할 수 있다.")
    void updateTest() {
        voucher.setStatus(VoucherStatus.INVALID);
        voucherRepository.update(voucher);
        Optional<Voucher> findVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get().getVoucherStatus(), is(VoucherStatus.INVALID));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 전체삭제 할 수 있다.")
    void deleteAllTest() {
        voucherRepository.deleteById(voucher.getVoucherId());
        List<Voucher> vouchers = voucherRepository.getAllVoucher();
        assertThat(vouchers.isEmpty(), is(true));
    }

}