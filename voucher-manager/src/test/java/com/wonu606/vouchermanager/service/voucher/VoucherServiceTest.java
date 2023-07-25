package com.wonu606.vouchermanager.service.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.service.voucher.converter.VoucherServiceConverterManager;
import com.wonu606.vouchermanager.service.voucher.factory.VoucherFactory;
import com.wonu606.vouchermanager.service.voucher.factory.util.UUIDGenerator;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("VoucherService 테스트")
public class VoucherServiceTest {

    private VoucherRepository repository;
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        repository = mock(VoucherRepository.class);
        VoucherFactory factory = new VoucherFactory(new FixedUUIDGenerator());
        VoucherServiceConverterManager converterManager = new VoucherServiceConverterManager();
        voucherService = new VoucherService(repository, factory, converterManager);
    }

    @Test
    @DisplayName("createVoucher_유효한 파리미터_바우처를 생성한다.")
    void createVoucher_WithValidParameters_VoucherIsCreated() {
        // Given
        double expectedDiscountValue = 50.0;
        VoucherCreateParam param = new VoucherCreateParam("fixed", expectedDiscountValue);
        VoucherInsertResultSet successResultSet = new VoucherInsertResultSet(1);
        given(repository.insert(any())).willReturn(successResultSet);

        VoucherInsertQuery expectedQuery = new VoucherInsertQuery(
                FixedAmountVoucher.class.getSimpleName(),
                FixedUUIDGenerator.FIXED_UUID.toString(), expectedDiscountValue);

        // When
        voucherService.createVoucher(param);

        // Then
        ArgumentCaptor<VoucherInsertQuery> argument = ArgumentCaptor.forClass(
                VoucherInsertQuery.class);
        then(repository).should().insert(argument.capture());
        VoucherInsertQuery actualQuery = argument.getValue();
        assertThat(actualQuery).usingRecursiveComparison().isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("getVoucherList_바우처가 존재한다면_바우처 리스트를 반환한다.")
    void getVoucherList_WhenVouchersExist_ReturnsListOfVouchers() {
        // Given
        VoucherResultSet voucherResultSet = new VoucherResultSet(
                FixedAmountVoucher.class.getSimpleName(),
                FixedUUIDGenerator.FIXED_UUID.toString(), 50.0);
        given(repository.findAll()).willReturn(List.of(voucherResultSet));

        VoucherResult expectedResult = new VoucherResult(voucherResultSet.getUuid(),
                voucherResultSet.getVoucherClassType(), voucherResultSet.getDiscountValue());

        // When
        List<VoucherResult> actualVoucherResults = voucherService.getVoucherList();

        // Then
        assertThat(actualVoucherResults).hasSize(1);
        assertThat(actualVoucherResults).usingRecursiveFieldByFieldElementComparator()
                .contains(expectedResult);
    }

    private static class FixedUUIDGenerator extends UUIDGenerator {

        public static UUID FIXED_UUID = UUID.fromString("42424242-4242-4242-4242-424242424242");

        @Override
        public UUID generateUUID() {
            return FIXED_UUID;
        }
    }
}
