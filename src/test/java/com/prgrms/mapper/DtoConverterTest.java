package com.prgrms.mapper;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherRegistry;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.FixedDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DtoConverterTest {

    DtoConverter dtoConverter;

    @BeforeEach
    void setUp(){
        dtoConverter = new DtoConverter();
    }

    @Test
    @DisplayName("voucher 리스트인 일급컬렉션을 VoucherResponse로 잘 바꾸는지 확인하다. ")
    public void GivenVoucherRegistry_WhenConvert_ThenVoucherResponse() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(),new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(),new FixedDiscount(30), VoucherType.FIXED_AMOUNT_VOUCHER);
        VoucherResponse voucherResponse1 = new VoucherResponse(voucher1);
        VoucherResponse voucherResponse2 = new VoucherResponse(voucher2);
        List<Voucher> vouchers = List.of(voucher1,voucher2);
        VoucherRegistry voucherRegistry = new VoucherRegistry(vouchers);

        //when
        List<VoucherResponse> result = dtoConverter.convertVoucherResponse(voucherRegistry);

        //then
        assertThat(result).contains(voucherResponse1,voucherResponse2);
    }
}
