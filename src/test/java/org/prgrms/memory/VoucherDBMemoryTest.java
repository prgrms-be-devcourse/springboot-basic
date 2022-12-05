package org.prgrms.memory;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wix.mysql.EmbeddedMysql;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(JdbcBase.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("jdbc")
public class VoucherDBMemoryTest {

    private EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(10000)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
            .addSchema("voucher_app", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    private VoucherDBMemory memory;

    @BeforeEach
    void clear() {
        memory.deleteAll();
    }

    @DisplayName("바우처를 저장하고 저장한 바우처를 반환한다")
    @Test
    void test1() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(300L),
            LocalDateTime.now());
        //when
        Voucher saved = memory.save(voucher);
        //then
        assertThat(voucher).isEqualTo(saved);
    }

    @DisplayName("새로 저장하는 바우처의 id가 이미 있을 경우 에러를 던진다")
    @Test
    void test1_1() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(300L),
            LocalDateTime.now());
        memory.save(voucher);
        //when&then
        assertThrows(DuplicateKeyException.class, () -> memory.save(voucher));
    }

    @DisplayName("저장된 모든 바우처의 정보를 반환한다")
    @Test
    void test2() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L),
            LocalDateTime.now().withNano(0));
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(10L),
            LocalDateTime.now().withNano(0));
        memory.save(voucher1);
        memory.save(voucher2);
        //when
        List<Voucher> voucherList = memory.findAll();
        //then
        assertEquals(voucherList.size(), 2);
        assertThat(voucherList).contains(voucher1, voucher2);

    }

    @DisplayName("바우처의 id로 해당 바우처 정보를 가져온다")
    @Test
    void test3() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(600L),
            LocalDateTime.now().withNano(0));
        Voucher saved = memory.save(voucher);
        //when
        Optional<Voucher> foundVoucher = memory.findById(saved.getVoucherId());
        //then
        assertThat(saved).isEqualTo(foundVoucher.get());
    }

    @DisplayName("바우처의 id로 해당 바우처 정보를 삭제한다")
    @Test
    void test4() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(50L),
            LocalDateTime.now().withNano(0));
        Voucher saved = memory.save(voucher);
        //when
        memory.deleteById(saved.getVoucherId());
        //then
        assertEquals(Optional.empty(), memory.findById(saved.getVoucherId()));
    }

    @DisplayName("저장된 모든 바우처 정보를 삭제한다")
    @Test
    void test5() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L),
            LocalDateTime.now());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(10L),
            LocalDateTime.now());
        memory.save(voucher1);
        memory.save(voucher2);
        //when
        memory.deleteAll();
        List<Voucher> all = memory.findAll();
        //then
        assertEquals(all.size(), 0);
    }

    @DisplayName("할인 금액을 업데이트한 바우처 정보를 리턴한다")
    @Test
    void test6() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        memory.save(voucher);
        //when
        Voucher updateAmount = voucher.changeAmountValue(3000L);
        Voucher updatedVoucher = memory.update(updateAmount);
        //then
        assertEquals(updateAmount, updatedVoucher);
    }

    @DisplayName("존재하지 않는 Id로 업데이트 시 NoSuchElementException 을 던진다.")
    @Test
    void test6_1() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        Voucher updateAmount = voucher.changeAmountValue(300L);
        //when&then
        assertThrows(NoSuchElementException.class, () -> memory.update(updateAmount));
    }

    @DisplayName("정해진 기간범주에 해당하는 바우처를 리턴한다")
    @Test
    void test7() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        memory.save(voucher1);
        memory.save(voucher2);
        //when
        List<Voucher> byCreateDate = memory.findByCreateDate(voucher1.getDate().toString(),
            voucher2.getDate().toString());
        //then
        assertThat(byCreateDate).contains(voucher1, voucher2);
    }

    @DisplayName("타입별 바우처를 리턴한다")
    @Test
    void test8() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        String fixed = "FIXED";
        memory.save(voucher1);
        memory.save(voucher2);
        //when
        List<Voucher> byType = memory.findByType(fixed);
        //then
        assertEquals(byType.size(), 1);
    }

}
