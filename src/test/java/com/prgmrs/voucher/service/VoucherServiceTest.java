package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.enums.VoucherSelectionType;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.validator.VoucherValidator;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("바우처 서비스 레이어를 테스트한다.")

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherValidator voucherValidator;

    public VoucherServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("바우처 생성을 테스트한다.")
    void CreateVoucher_VoucherRequest_SameVoucher() {
        // Given
        String token = "500";
        DiscountValue discountValue = new DiscountValue(500);
        long value = 500;
        Optional<Long> optionalLong = Optional.of(value);
        VoucherRequest voucherRequest = new VoucherRequest(VoucherSelectionType.FIXED_AMOUNT_VOUCHER, token);
        given(voucherValidator.isAmountValid(VoucherSelectionType.FIXED_AMOUNT_VOUCHER, discountValue)).willReturn(true);
        given(voucherValidator.stringToLongConverter(token)).willReturn(optionalLong);
        given(voucherValidator.isValidIntegerString(token)).willReturn(true);


        // When
        VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);

        // Then
        Voucher voucher = voucherResponse.voucher();
        assertThat(voucher).isNotNull();
        assertThat(((FixedAmountDiscountStrategy) voucher.discountStrategy()).amount().value()).isEqualTo(500L);
        verify(voucherRepository, times(1)).save(any(Voucher.class));
    }

    @Test
    @DisplayName("바우처 리스트를 조회한다.")
    void FindAll_NoParam_SameVoucherList() {
        // Given
        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        given(voucherRepository.findAll()).willReturn(voucherList);

        // When
        VoucherListResponse voucherListResponse = voucherService.findAll();

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
            .containsExactlyInAnyOrder(voucher1, voucher2);
        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("유저 이름에 해당하는 할당된 바우처 리스트를 조회한다.")
    void GetAssignedVoucherListByUsername_Username_SameVoucherListResponse() {
        // Given
        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);

        given(voucherRepository.getAssignedVoucherListByUsername(user.username())).willReturn(voucherList);

        // When
        VoucherListResponse voucherListResponse = voucherService.getAssignedVoucherListByUsername(username);

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
            .containsExactlyInAnyOrder(voucher1, voucher2);
        verify(voucherRepository, times(1)).getAssignedVoucherListByUsername(username);
    }

    @Test
    @DisplayName("할당되지 않은 바우처 리스트를 조회한다.")
    void GetNotAssignedVoucher_NoParam_VoucherListResponseSameAsGivenVoucher() {
        // Given
        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        given(voucherRepository.getNotAssignedVoucherList()).willReturn(voucherList);

        // When
        VoucherListResponse voucherListResponse = voucherService.getNotAssignedVoucher();

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
            .containsExactlyInAnyOrder(voucher1, voucher2);
        verify(voucherRepository, times(1)).getNotAssignedVoucherList();
    }

    @Test
    @DisplayName("할당된 바우처 리스트를 조회한다.")
    void GetAssignedVoucherList_NoParam_VoucherListResponseSameAsGivenVoucher() {
        // Given
        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        given(voucherRepository.getAssignedVoucherList()).willReturn(voucherList);

        // When
        VoucherListResponse voucherListResponse = voucherService.getAssignedVoucherList();

        // Then
        List<Voucher> assignedVoucherList = voucherListResponse.voucherList();
        assertThat(assignedVoucherList).hasSize(2)
            .containsExactlyInAnyOrder(voucher1, voucher2);
        verify(voucherRepository, times(1)).getAssignedVoucherList();
    }
}