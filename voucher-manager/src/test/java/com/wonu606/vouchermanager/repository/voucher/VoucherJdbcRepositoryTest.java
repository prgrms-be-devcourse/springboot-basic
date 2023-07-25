package com.wonu606.vouchermanager.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.reader.VoucherJdbcReader;
import com.wonu606.vouchermanager.repository.voucher.reader.VoucherReader;
import com.wonu606.vouchermanager.repository.voucher.reader.rowmapper.VoucherReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.repository.voucher.store.VoucherJdbcStore;
import com.wonu606.vouchermanager.repository.voucher.store.VoucherStore;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("VoucherResultSetJdbcRepository 테스트")
class VoucherJdbcRepositoryTest {

    private VoucherRepository voucherJdbcRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        VoucherReader reader = new VoucherJdbcReader(namedParameterJdbcTemplate,
                new VoucherReaderRowMapperManager());
        VoucherStore store = new VoucherJdbcStore(namedParameterJdbcTemplate);
        voucherJdbcRepository = new VoucherJdbcRepository(reader, store);
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("insert_새 Voucher라면_저장된다.")
    public void insert_NewVoucher_ReturnInsertedVoucher(VoucherInsertQuery query) {
        // Given
        VoucherInsertResultSet expected = new VoucherInsertResultSet(1);

        // When
        VoucherInsertResultSet actual = voucherJdbcRepository.insert(query);

        // Then
        Assertions.assertThat(actual.getAffectedRowsCount())
                .isEqualTo(expected.getAffectedRowsCount());
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("findById_존재하는 Voucher라면_해당 Voucher를 반환한다.")
    public void findById_ExistingVoucher_ReturnVoucher(VoucherInsertQuery query) {
        // Given
        voucherJdbcRepository.insert(query);
        VoucherFindQuery voucherFindQuery = new VoucherFindQuery(query.getVoucherId());
        VoucherResultSet expected = new VoucherResultSet(query.getVoucherClassSimpleName(),
                query.getVoucherId(), query.getDiscountValue());

        // When
        Optional<VoucherResultSet> actual = voucherJdbcRepository.findById(voucherFindQuery);

        // Then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("findAll_조건 없음_모든 바우처를 반환한다.")
    public void findAll_NoConditions_ReturnAllVouchers(VoucherInsertQuery query) {
        // Given
        voucherJdbcRepository.insert(query);
        VoucherResultSet expectedResultSet = new VoucherResultSet(
                query.getVoucherClassSimpleName(), query.getVoucherId(),
                query.getDiscountValue());

        // When
        List<VoucherResultSet> actual = voucherJdbcRepository.findAll();

        // Then
        assertThat(actual).hasSize(1);
        assertThat(actual).usingRecursiveComparison().isEqualTo(List.of(expectedResultSet));
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    public void deleteById_ExistingVoucher_VoucherIsDeleted(VoucherInsertQuery query) {
        // Given
        voucherJdbcRepository.insert(query);
        VoucherDeleteQuery voucherDeleteQuery = new VoucherDeleteQuery(query.getVoucherId());

        // When
        voucherJdbcRepository.deleteById(voucherDeleteQuery);
        List<VoucherResultSet> actual = voucherJdbcRepository.findAll();

        // Then
        assertThat(actual).hasSize(0);
    }

    static Stream<Arguments> givenVoucherInsertQuery() {
        VoucherInsertQuery voucherInsertQuery = new VoucherInsertQuery("fixed",
                "eaea93d1-08e4-4311-ad44-7b6a626c1a71", 10.0);

        return Stream.of(Arguments.of(voucherInsertQuery));
    }
}
