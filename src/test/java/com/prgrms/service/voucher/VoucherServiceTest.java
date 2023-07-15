package com.prgrms.service.voucher;

import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.model.KeyGenerator;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.DiscountCreator;
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

    private final int ID = 1;
    private final double DISCOUNT_AMOUNT = 20;

    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private VoucherConverter voucherConverter;
    @Mock
    private KeyGenerator keyGenerator;
    @Mock
    VoucherCreator voucherCreator;
    @Mock
    DiscountCreator discountCreator;

    private VoucherService voucherService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository, voucherConverter, keyGenerator, voucherCreator, discountCreator);
    }

    @Test
    @DisplayName("만들고자 하는 바우처를 createVoucher()로 만들었을 때 기대값과 같은 바우처를 반환한다.")
    void createVoucher_RepositoryInsertVoucher_Equals() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(DISCOUNT_AMOUNT);
        Voucher createdVoucher = new FixedAmountVoucher(ID, discount, voucherType);

        when(keyGenerator.make()).thenReturn(ID);
        when(discountCreator.createDiscount(voucherType, DISCOUNT_AMOUNT)).thenReturn(discount);
        when(voucherCreator.createVoucher(ID, voucherType, discount)).thenReturn(createdVoucher);
        when(voucherRepository.insert(createdVoucher)).thenReturn(createdVoucher);


        //when
        Voucher result = voucherService.createVoucher(voucherType,DISCOUNT_AMOUNT);

        //then
        assertThat(result)
                .isNotNull()
                .isEqualTo(createdVoucher);
        verify(keyGenerator, times(1)).make();
        verify(discountCreator, times(1)).createDiscount(voucherType, DISCOUNT_AMOUNT);
        verify(voucherCreator, times(1)).createVoucher(ID, voucherType, discount);
        verify(voucherRepository, times(1)).insert(createdVoucher);
    }

    @Test
    @DisplayName("몇 개의 바우처 정책을 List로 만들어 저장소에 저장한 결과와 getAllVocherList의 결과는 같다.")
    void getAllVoucherList_RepositoryListVoucherList_Equals() {
        //given
        Voucher createdVoucher1 = new FixedAmountVoucher(ID, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(ID, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);

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
