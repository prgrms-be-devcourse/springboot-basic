package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.devcourse.springbootbasic.application.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
@ActiveProfiles("test")
class VoucherServiceTest {

    VoucherService sut;
    
    VoucherRepository repository;
    
    @BeforeEach
    void init() {
        repository = mock(VoucherRepository.class);
        sut = new VoucherService(repository);
    }

    @ParameterizedTest
    @DisplayName("새로운 바우처가 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void createVoucher_VoucherParam_InsertAndReturnVoucher(Voucher voucher) {
        given(repository.insert(any(Voucher.class))).willReturn(voucher);

        Voucher result = sut.createVoucher(voucher);

        assertThat(result).isSameAs(voucher);
    }

    @Test
    @DisplayName("생성된 바우처가 리스트 형태로 반환되면 성공한다.")
    void findVouchers_ParamVoid_ReturnVoucherList() {
        given(repository.findAll()).willReturn(vouchers);

        List<Voucher> result = sut.findVouchers();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 검색하는 경우 성공한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamExistVoucher_ReturnVoucher(Voucher voucher) {
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(voucher));

        Voucher foundVoucher = sut.findVoucherById(voucher.getVoucherId());

        assertThat(foundVoucher).isSameAs(voucher);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 아이디로 검색하는 경우 실패한다.")
    @MethodSource("provideVouchers")
    void findVoucherById_ParamNotExistVoucher_Exception(Voucher voucher) {
        given(repository.findById(any(UUID.class))).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.findVoucherById(voucher.getVoucherId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @Test
    @DisplayName("모든 바우처를 제거한다.")
    void deleteAllVouchers_ParamVoid_DeleteAllVouchers() {
        sut.deleteAllVouchers();

        assertThat(sut.findVouchers()).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("아이디로 바우처 제거한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherById_ParamVoucherId_DeleteVoucher(Voucher voucher) {
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(voucher));

        Voucher deletedVoucher = sut.deleteVoucherById(voucher.getVoucherId());

        assertThat(deletedVoucher).isSameAs(voucher);
    }

    @ParameterizedTest
    @DisplayName("없는 바우처를 아이디로 제거하면 실패한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherById_ParamNotExistVoucherId_Exception(Voucher voucher) {
        given(repository.findById(any(UUID.class))).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.deleteVoucherById(voucher.getVoucherId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 소유한 바우처를 리스트로 반환한다.")
    @MethodSource("provideVouchers")
    void findVouchersByCustomerId_ParamCustomerId_ReturnVoucher(Voucher voucher) {
        given(repository.findAllByCustomerId(any())).willReturn(vouchers);

        List<Voucher> vouchers = sut.findVouchersByCustomerId(voucher.getCustomerId());

        assertThat(vouchers).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 고객, 바우처 아이디로 삭제 시 성공한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherCustomerByCustomerIdAndVoucherId_ParamIds_DeleteVoucher(Voucher voucher) {
        given(repository.findByCustomerIdAndVoucherId(any(), any())).willReturn(Optional.of(voucher));

        Voucher deletedVoucher = sut.deleteVoucherCustomerByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId());

        assertThat(deletedVoucher).isSameAs(voucher);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 고객, 바우처 아이디로 삭제 시 실패한다.")
    @MethodSource("provideVouchers")
    void deleteVoucherCustomerByCustomerIdAndVoucherId_ParamIds_Exception(Voucher voucher) {
        given(repository.findByCustomerIdAndVoucherId(any(), any())).willReturn(Optional.empty());

        Exception exception = catchException(() -> sut.deleteVoucherCustomerByCustomerIdAndVoucherId(voucher.getCustomerId(), voucher.getVoucherId()));

        assertThat(exception).isInstanceOf(InvalidDataException.class);
    }

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true)
    );

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"), customers.get(0).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0"), customers.get(0).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240"), customers.get(1).getCustomerId()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"), customers.get(1).getCustomerId())
    );

}
