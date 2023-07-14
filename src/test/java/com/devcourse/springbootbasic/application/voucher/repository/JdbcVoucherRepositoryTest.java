package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.customer.repository.CustomerRepository;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void init() {
        var mysqlConfig = MysqldConfig.aMysqldConfig(Version.v8_0_17)
                .withCharset(Charset.UTF8)
                .withPort(8070)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_system", ScriptResolver.classPathScript("test-schema.sql"))
                .start();
        customers.forEach(customer -> customerRepository.insert(customer));
    }

    @BeforeEach
    void cleanup() {
        voucherRepository.deleteAll();
    }

    @AfterAll
    void destroy() {
        embeddedMysql.stop();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 추가 시 성공한다.")
    @MethodSource("provideVouchers")
    void insert_ParamNotExistVoucher_InsertAndReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 추가 시 실패한다.")
    @MethodSource("provideVouchers")
    void insert_ParamExistVoucher_Exception(Voucher voucher) {
        voucherRepository.insert(voucher);

        Exception exception = catchException(() -> voucherRepository.insert(voucher));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideVouchers")
    void update_ParamExistVoucher_UpdateAndReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);
        Voucher newVoucher = new Voucher(voucher.getVoucherId(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), voucher.getCustomerId());

        voucherRepository.update(newVoucher);
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher).isNotEmpty();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(newVoucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 갱신 시 실패한다.")
    @MethodSource("provideVouchers")
    void update_ParamNotExistVoucher_Exception(Voucher voucher) {
        Exception exception = catchException(() -> voucherRepository.update(voucher));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("모든 바우처 조회한다.")
    void findAllVouchers_ParamVoid_ReturnAllVouchers() {
        vouchers.forEach(voucher -> voucherRepository.insert(voucher));

        List<Voucher> allVouchers = voucherRepository.findAll();

        assertThat(allVouchers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 조회 시 성공한다.")
    @MethodSource("provideVouchers")
    void findById_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않은 바우처를 아이디로 조회 시 실패한다.")
    @MethodSource("provideVouchers")
    void findById_ParamNotExistVoucher_ReturnOptionalEmpty(Voucher voucher) {
        Optional<Voucher> result = voucherRepository.findById(voucher.getVoucherId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("모든 바우처 삭제한다.")
    void deleteAll_ParamVoid_DeleteAllVouchers() {
        voucherRepository.deleteAll();

        assertThat(voucherRepository.findAll()).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 제거하면 성공한다.")
    @MethodSource("provideVouchers")
    void deleteById_ParamExistVoucher_DeleteVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        voucherRepository.deleteById(voucher.getVoucherId());

        Optional<Voucher> maybeNull = voucherRepository.findById(voucher.getVoucherId());
        assertThat(maybeNull).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 고객, 바우처 아이디로 검색하면 성공한다.")
    @MethodSource("provideVouchers")
    void findByCustomerIdAndVoucherId_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        Optional<Voucher> foundVoucher = voucherRepository.findByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());

        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 고객, 바우처 아이디로 검색하면 실패한다.")
    @MethodSource("provideVouchers")
    void findByCustomerIdAndVoucherId_ParamNotExistVoucher_Exception(Voucher voucher) {
        Optional<Voucher> maybeNull = voucherRepository.findByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());

        assertThat(maybeNull).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 조회 시 바우처를 반환한다.")
    @MethodSource("provideVouchers")
    void findAllByCustomerId_ParamVoid_ReturnVoucherList(Voucher voucher) {
        voucherRepository.insert(voucher);

        List<Voucher> list = voucherRepository.findAllByCustomerId(voucher.getCustomerId());

        assertThat(list).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("고객, 바우처 아이디로 제거하면 성공한다.")
    @MethodSource("provideVouchers")
    void deleteByCustomerIdAndVoucherId_ParamExistVoucher_DeleteVoucher(Voucher voucher) {
        voucherRepository.insert(voucher);

        voucherRepository.deleteByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());

        Optional<Voucher> maybeNull = voucherRepository.findByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());
        assertThat(maybeNull).isEmpty();
    }

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", true),
            new Customer(UUID.randomUUID(), "딸기", false)
    );

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), customers.get(0).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "2"), customers.get(0).getCustomerId())
    );

}
