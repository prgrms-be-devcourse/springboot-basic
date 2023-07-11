package com.wonu606.vouchermanager.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.repository.voucher.MappingVoucherVoucherRepository;
import com.wonu606.vouchermanager.repository.voucher.VoucherResultSetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("MappingVoucherRepository 테스트")
class MappingVoucherVoucherRepositoryTest {

    private VoucherResultSetRepository voucherResultSetRepository;

    private MappingVoucherVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherResultSetRepository = mock(VoucherResultSetRepository.class);
        voucherRepository = new MappingVoucherVoucherRepository(voucherResultSetRepository);
    }

    @Test
    @DisplayName("save_바우처_바우처를 저장하고 반환한다.")
    void save_VoucherProvided_ReturnVoucher() {
        // given
        Voucher voucher = mock(Voucher.class);

        // then
        voucherRepository.save(voucher);

        // when
        verify(voucherResultSetRepository, times(1)).save(voucher);
    }

    @Test
    @DisplayName("findById_존재하는 uuid_바우처로 변환하여 반환한다.")
    void findById_ExistingUuid_ReturnsExpectedVoucher() {
        // given
        FixedAmountVoucher expectedVoucher = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0)
        );
        VoucherResultSet enteredResultSet = new VoucherResultSet(
                expectedVoucher.getUuid(),
                expectedVoucher.getClass().getSimpleName(),
                expectedVoucher.getDiscountValue()
        );

        given(voucherResultSetRepository.findById(expectedVoucher.getUuid()))
                .willReturn(Optional.of(enteredResultSet));

        // when
        Optional<Voucher> actualVoucher = voucherRepository.findById(expectedVoucher.getUuid());

        // then
        assertThat(actualVoucher.isPresent()).isTrue();
        assertThat(actualVoucher.get()).isEqualTo(expectedVoucher);
    }

    @ParameterizedTest
    @MethodSource("givenVouchers")
    @DisplayName("findAll_빈 인수_저장된 모든 바우처를 반환한다.")
    void findAll_EmptyArgument_SavedAllVoucher(Voucher expectedVoucher1, Voucher expectedVoucher2) {
        // given
        List<Voucher> expectedVoucherList = new ArrayList<>();
        expectedVoucherList.add(expectedVoucher1);
        expectedVoucherList.add(expectedVoucher2);

        VoucherResultSet expectedResultSet1 = new VoucherResultSet(
                expectedVoucher1.getUuid(), expectedVoucher1.getClass().getSimpleName(),
                expectedVoucher1.getDiscountValue());
        VoucherResultSet expectedResultSet2 = new VoucherResultSet(
                expectedVoucher2.getUuid(), expectedVoucher2.getClass().getSimpleName(),
                expectedVoucher2.getDiscountValue());

        List<VoucherResultSet> expectedVoucherResultSetList = new ArrayList<>();
        expectedVoucherResultSetList.add(expectedResultSet1);
        expectedVoucherResultSetList.add(expectedResultSet2);

        given(voucherResultSetRepository.findAll()).willReturn(expectedVoucherResultSetList);

        // then
        List<Voucher> actualVoucherList = voucherRepository.findAll();

        // when
        assertThat(actualVoucherList).hasSameSizeAs(expectedVoucherList);
        assertThat(actualVoucherList).containsExactlyInAnyOrderElementsOf(expectedVoucherList);
    }

    @Test
    @DisplayName("deleteById_UUID_같은 UUID를 반환한다.")
    void deleteById_Uuid_ReturnsSameUuid() {
        // given
        UUID expectedUuid = UUID.randomUUID();

        // when
        voucherRepository.deleteById(expectedUuid);

        // then
        verify(voucherResultSetRepository, times(1)).deleteById(expectedUuid);
    }

    @Test
    @DisplayName("deleteAll_빈 인자_ResultSetRepository의 deleeteAll을 실행한다.")
    void deleteAll_EmptyArgument_ExecuteVoucherResultSetRepositoryDeleteAllMethod() {
        // when
        voucherRepository.deleteAll();

        // then
        verify(voucherResultSetRepository, times(1)).deleteAll();
    }

    static Stream<Arguments> givenVouchers() {
        Voucher expectedVoucher1 = new PercentageVoucher(
                UUID.randomUUID(), new PercentageDiscountValue(50.0));
        Voucher expectedVoucher2 = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));

        return Stream.of(Arguments.of(expectedVoucher1, expectedVoucher2));
    }
}
