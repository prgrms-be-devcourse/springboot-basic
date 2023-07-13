package com.prgrms.service.voucher;

import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.model.KeyGenerator;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private VoucherConverter voucherConverter;
    @Mock
    private KeyGenerator keyGenerator;
    @Mock
    VoucherCreator voucherCreator;

    private VoucherService voucherService;
    private int id = 1;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository,voucherConverter,keyGenerator,voucherCreator);
    }

    @Test
    @DisplayName("전달받은 바우처를 레파지토리에 잘 전달하여 저장소에 저장되는지 확인한다.")
    void createVoucher_RepositoryInsertVoucher_Equals() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(10);
        VoucherRequest voucherRequest = new VoucherRequest(voucherType, discount);
        Voucher createdVoucher = new FixedAmountVoucher(id, discount, voucherType);
        when(voucherRepository.insert(any(Voucher.class))).thenReturn(createdVoucher);

        //when
        Voucher result = voucherService.createVoucher(voucherRequest);

        //then
        assertThat(result)
                .isNotNull()
                .isEqualTo(createdVoucher);
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("저장된 바우처 정책을 잘 출력하는지 확인한다.")
    void getAllVoucherList_RepositoryListVoucherList_Equals() {
        //given
        Voucher createdVoucher1 = new FixedAmountVoucher(id, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(id, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);

        VoucherResponse voucherResponse1 = new VoucherResponse(createdVoucher1);
        VoucherResponse voucherResponse2 = new VoucherResponse(createdVoucher2);

        List<VoucherResponse> expected = List.of(voucherResponse1, voucherResponse2);
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);

        Vouchers voucherRegistry = new Vouchers(list);

        when(voucherRepository.getAllVoucher()).thenReturn(voucherRegistry);
        when(voucherConverter.convertVoucherResponse(any(Vouchers.class))).thenReturn(expected);

        //when
        List<VoucherResponse> result = voucherService.getAllVoucherList();

        //then
        assertThat(result)
                .isNotNull()
                .containsOnly(voucherResponse1, voucherResponse2);
        verify(voucherRepository, times(1)).getAllVoucher();
    }
}
