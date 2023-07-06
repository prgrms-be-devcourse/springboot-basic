package com.prgrms.service.voucher;

import com.prgrms.model.dto.mapper.DtoConverter;
import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.repository.voucher.VoucherRepository;
import com.prgrms.model.voucher.VoucherCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private VoucherCreator voucherCreator;
    @Mock
    private DtoConverter dtoConverter;

    private VoucherService voucherService;
    private UUID voucherId1;
    private UUID voucherId2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository, voucherCreator, dtoConverter);
        voucherId1 = UUID.randomUUID();
        voucherId2 = UUID.randomUUID();

    }

    @Test
    @DisplayName("전달받은 바우처를 레파지토리에 잘 전달하여 저장소에 저장되는지 확인한다.")
    public void createVoucher_RepositoryInsertVoucher_Equals() {
        //given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.FIXED_AMOUNT_VOUCHER, new FixedDiscount(10));
        Voucher createdVoucher = new FixedAmountVoucher(voucherId1, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        when(voucherCreator.createVoucher(any(Discount.class), any(VoucherType.class)))
                .thenReturn(createdVoucher);
        when(voucherRepository.insert(any(Voucher.class))).thenReturn(createdVoucher);

        //when
        Voucher result = voucherService.createVoucher(voucherRequest);

        //then
        assertThat(result)
                .isNotNull()
                .isEqualTo(createdVoucher);
        verify(voucherCreator, times(1)).createVoucher(any(Discount.class), any(VoucherType.class));
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("저장된 바우처 정책을 잘 출력하는지 확인한다.")
    public void getAllVoucherList_RepositoryListVoucherList_Equals() {
        //given
        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId2, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);
        VoucherResponse voucherResponse1 = new VoucherResponse(createdVoucher1);
        VoucherResponse voucherResponse2 = new VoucherResponse(createdVoucher2);
        List<VoucherResponse> expected = List.of(voucherResponse1, voucherResponse2);
        List<Voucher> list = List.of(createdVoucher1, createdVoucher2);
        VoucherRegistry voucherRegistry = new VoucherRegistry(list);
        when(voucherRepository.getAllVoucher()).thenReturn(voucherRegistry);
        when(dtoConverter.convertVoucherResponse(any(VoucherRegistry.class))).thenReturn(expected);

        //when
        List<VoucherResponse> result = voucherService.getAllVoucherList();

        //then
        assertThat(result)
                .isNotNull()
                .containsOnly(voucherResponse1,voucherResponse2);
        verify(voucherRepository, times(1)).getAllVoucher();
    }
}
