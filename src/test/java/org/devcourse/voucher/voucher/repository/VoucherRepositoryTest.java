package org.devcourse.voucher.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.devcourse.voucher.application.voucher.repository.VoucherRepository;
import org.devcourse.voucher.core.exception.DataUpdateFailException;
import org.devcourse.voucher.application.voucher.model.FixedAmountVoucher;
import org.devcourse.voucher.application.voucher.model.PercentDiscountVoucher;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJdbcTest
@ActiveProfiles("test")
class VoucherRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    private VoucherRepository voucherRepository;

    Pageable pageable;

    private final FixedAmountVoucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);

    @BeforeAll
    static void setup() {
        MysqldConfig config = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(config)
                .addSchema("test-voucher", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void init() {
        pageable = Pageable.ofSize(5);
    }

    private List<Voucher> vouchersStubCreate() {
        List<Voucher> vouchers = new ArrayList<>(List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 35),
                new FixedAmountVoucher(UUID.randomUUID(), 500),
                new PercentDiscountVoucher(UUID.randomUUID(), 90),
                new FixedAmountVoucher(UUID.randomUUID(), 4500)
        ));

        vouchers.sort(new Comparator<Voucher>() {
            @Override
            public int compare(Voucher v1, Voucher v2) {
                return v1.getVoucherId().toString().compareTo(v2.getVoucherId().toString());
            }
        });

        return vouchers;
    }

    @Test
    @DisplayName("데이터베이스에 값이 잘 들어가는지 확인하는 테스트")
    void insertTest() {
        voucherRepository.insert(newVoucher);

        List<Voucher> vouchers = voucherRepository.findAll(pageable).getContent();

        assertThat(vouchers.get(0)).usingRecursiveComparison().isEqualTo(newVoucher);
    }

    @Test
    @DisplayName("중복된 데이터를 넣으려고 할 경우 예외가 발생하는지 테스트")
    void duplicateCustomerInsertTest() {
        voucherRepository.insert(newVoucher);

        assertThatExceptionOfType(DuplicateKeyException.class)
                .isThrownBy(() -> voucherRepository.insert(newVoucher));
    }

    @Test
    @DisplayName("DB에 들어있는 데이터를 업데이트 했을 때 잘 업데이트가 되는지 테스트")
    void customerUpdateTest() {
        voucherRepository.insert(newVoucher);
        newVoucher.setDiscount(1000);

        voucherRepository.update(newVoucher);
        Voucher customer = voucherRepository.findAll(pageable).getContent().get(0);

        assertThat(customer.getDiscount()).isEqualTo(1000);
    }

    @Test
    @DisplayName("DB에 존재하지 않는 데이터를 업데이트 시도시 예외가 발생하는지 테스트")
    void notExistsCustomerUpdateTest() {

        assertThatExceptionOfType(DataUpdateFailException.class)
                .isThrownBy(() -> voucherRepository.update(newVoucher));
    }

    @Test
    @DisplayName("DB에 존재하는 데이터가 잘 조회되는지 테스트")
    void customerFindAllTest() {
        List<Voucher> stubs = vouchersStubCreate();

        for (Voucher voucher : stubs) {
            voucherRepository.insert(voucher);
        }
        List<Voucher> vouchers = voucherRepository.findAll(pageable).getContent();

        assertThat(vouchers).usingRecursiveComparison().isEqualTo(stubs);
    }

    @Test
    @DisplayName("DB에 존재하는 데이터가 전부 잘 삭제되는지 테스트")
    void customerDeleteAllTest() {
        List<Voucher> stubs = vouchersStubCreate();

        for (Voucher voucher : stubs) {
            voucherRepository.insert(voucher);
        }
        voucherRepository.deleteAll();

        assertThat(voucherRepository.findAll(pageable).getContent()).isEmpty();
    }
}