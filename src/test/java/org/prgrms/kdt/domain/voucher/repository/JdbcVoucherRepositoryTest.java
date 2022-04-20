package org.prgrms.kdt.domain.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfig;
import org.prgrms.kdt.domain.common.exception.ExceptionType;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.repository.JdbcCustomerRepository;
import org.prgrms.kdt.domain.voucher.exception.VoucherNotUpdatedException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({TestConfig.class})
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    JdbcCustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("park", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test", classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void cleanData() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @AfterAll
    void stopDatabase() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처가 정상적으로 저장된다.")
    void save() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        //when
        UUID savedId = voucherRepository.save(voucher);
        //then
        assertThat(voucherId).isEqualTo(savedId);
    }

    @Test
    @DisplayName("바우처 ID로 바우처를 조회한다.")
    void findById() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        //when
        voucherRepository.save(voucher);
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);
        //then
        assertThat(findVoucher.get()).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("저장된 바우처를 전부 조회한다.")
    void findAll() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        //when
        voucherRepository.save(voucher);
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("고객 ID를 통해 바우처를 조회할 수 있다.")
    void findByCustomerId() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        customerRepository.save(new Customer(customerId, "park", "d@naver.com", now, now));
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);
        //when
        List<Voucher> findVouchers = voucherRepository.findByCustomerId(customerId);
        //then
        assertThat(findVouchers.size()).isEqualTo(1);
        assertThat(findVouchers.get(0)).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 타입과 날짜를 통해 바우처를 조회할 수 있다.")
    void findByVoucherTypeAndDate() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        //when
        List<Voucher> findVouchers = voucherRepository.findByVoucherTypeAndDate(VoucherType.PERCENT_DISCOUNT, now.toLocalDate());
        //then
        assertThat(findVouchers.size()).isEqualTo(1);
        assertThat(findVouchers.get(0)).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처를 업데이트 할 수 있다.")
    void updateById() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        Voucher updateVoucher = new Voucher(voucherId, VoucherType.FIXED_AMOUNT, 20000L, now, now);
        //when
        int updateCnt = voucherRepository.updateById(updateVoucher);
        //then
        assertThat(updateCnt).isEqualTo(1);
    }

    @Test
    @DisplayName("잘못된 ID를 입력해 업데이트된 컬럼이 없을경우 예외가 발생한다.")
    void updateById_exception() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        Voucher updateVoucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 20000L, now, now);
        //when
        //then
        assertThatThrownBy(() -> voucherRepository.updateById(updateVoucher))
                .isInstanceOf(VoucherNotUpdatedException.class)
                .hasMessage(ExceptionType.NOT_UPDATED.getMsg());
    }

    @Test
    @DisplayName("입력받은 고객 ID를 통해 바우처를 고객에게 할당할 수 있다.")
    void updateCustomerId() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        customerRepository.save(new Customer(customerId, "park", "d@naver.com", now, now));
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        //when
        int updateCnt = voucherRepository.updateCustomerId(voucherId, customerId);
        //then
        assertThat(updateCnt).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처를 삭제할 수 있다.")
    void deleteById() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        //when
        int deletedRows = voucherRepository.deleteById(voucherId);
        //then
        assertThat(deletedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("저장된 바우처들을 전부 삭제한다.")
    void deleteAll() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now);
        voucherRepository.save(voucher);
        //when
        int deletedRows = voucherRepository.deleteAll();
        //then
        assertThat(deletedRows).isEqualTo(1);
    }

    @Test
    @DisplayName("고객이 가진 바우처를 삭제한다.")
    void deleteByCustomerId() {
        //given
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UUID customerId = UUID.randomUUID();
        customerRepository.save(new Customer(customerId, "park", "d@naver.com", now, now));
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new Voucher(voucherId, VoucherType.PERCENT_DISCOUNT, 10L, customerId, now, now);
        voucherRepository.save(voucher);
        //when
        int deletedRows = voucherRepository.deleteByCustomerId(customerId);
        //then
        assertThat(deletedRows).isEqualTo(1);
    }
}