package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UsernameRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.enums.DiscountType;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.validator.VoucherValidator;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    private Voucher voucherFixedAmountOf300;
    private Voucher voucherFixedAmountOf200;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        voucherFixedAmountOf300 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        voucherFixedAmountOf200 = new Voucher(voucherUuid2, discountStrategy2);
    }

    @Test
    @DisplayName("바우처 생성을 테스트한다.")
    void CreateVoucher_VoucherRequest_SameVoucher() {
        // Given
        VoucherRequest voucherRequest = new VoucherRequest("percent", "30");
        given(voucherValidator.convertToLongWithValidation("30", DiscountType.PERCENT_DISCOUNT)).willReturn(30L);

        // When
        VoucherResponse voucherResponse = voucherService.createVoucher(voucherRequest);

        // Then
        ArgumentCaptor<Voucher> voucherCaptor = ArgumentCaptor.forClass(Voucher.class);
        verify(voucherRepository, times(1)).save(voucherCaptor.capture());
        Voucher capturedVoucher = voucherCaptor.getValue();

        assertThat(voucherResponse).isNotNull();
        assertThat(capturedVoucher.voucherId()).isNotNull();
        assertThat(capturedVoucher.discountStrategy()).isInstanceOf(PercentDiscountStrategy.class);
        assertThat(((PercentDiscountStrategy) capturedVoucher.discountStrategy())
                .percent().value()).isEqualTo(30);
    }

    @Test
    @DisplayName("바우처 리스트를 조회한다.")
    void FindAll_NoParam_SameVoucherList() {
        // Given
        given(voucherRepository.findAll()).willReturn(Arrays.asList(voucherFixedAmountOf300, voucherFixedAmountOf200));

        // When
        VoucherListResponse voucherListResponse = voucherService.findAll();

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf300, voucherFixedAmountOf200);
        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("유저 이름에 해당하는 할당된 바우처 리스트를 조회한다.")
    void GetAssignedVoucherListByUsername_Username_SameVoucherListResponse() {
        // Given
        Username username = new Username("tyler");
        UsernameRequest usernameRequest = new UsernameRequest(username.value());
        given(voucherRepository.getAssignedVoucherListByUsername(username))
                .willReturn(Arrays.asList(voucherFixedAmountOf300, voucherFixedAmountOf200));

        // When
        VoucherListResponse voucherListResponse = voucherService.getAssignedVoucherListByUsername(usernameRequest);

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf300, voucherFixedAmountOf200);
        verify(voucherRepository, times(1)).getAssignedVoucherListByUsername(username);
    }

    @Test
    @DisplayName("할당되지 않은 바우처 리스트를 조회한다.")
    void GetNotAssignedVoucher_NoParam_VoucherListResponseSameAsGivenVoucher() {
        // Given
        given(voucherRepository.getNotAssignedVoucherList())
                .willReturn(Arrays.asList(voucherFixedAmountOf300, voucherFixedAmountOf200));

        // When
        VoucherListResponse voucherListResponse = voucherService.getNotAssignedVoucher();

        // Then
        List<Voucher> retrievedVoucherList = voucherListResponse.voucherList();
        assertThat(retrievedVoucherList).hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf300, voucherFixedAmountOf200);
        verify(voucherRepository, times(1)).getNotAssignedVoucherList();
    }

    @Test
    @DisplayName("할당된 바우처 리스트를 조회한다.")
    void GetAssignedVoucherList_NoParam_VoucherListResponseSameAsGivenVoucher() {
        // Given
        given(voucherRepository.getAssignedVoucherList()).
                willReturn(Arrays.asList(voucherFixedAmountOf300, voucherFixedAmountOf200));

        // When
        VoucherListResponse voucherListResponse = voucherService.getAssignedVoucherList();

        // Then
        List<Voucher> assignedVoucherList = voucherListResponse.voucherList();
        assertThat(assignedVoucherList).hasSize(2)
                .containsExactlyInAnyOrder(voucherFixedAmountOf300, voucherFixedAmountOf200);
        verify(voucherRepository, times(1)).getAssignedVoucherList();
    }
}