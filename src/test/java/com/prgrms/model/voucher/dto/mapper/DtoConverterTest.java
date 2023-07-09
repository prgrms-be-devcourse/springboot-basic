package com.prgrms.model.voucher.dto.mapper;

import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DtoConverterTest {

    private DtoConverter dtoConverter;
    private int idOne = 1;
    private int idTwo = 2;

    @BeforeEach
    void setUp() {
        dtoConverter = new DtoConverter();
    }

    @Test
    @DisplayName("voucher 리스트인 일급컬렉션을 VoucherResponse로 잘 바꾸는지 확인하다. ")
    public void convertVoucherResponse_EqualVoucherResponse_Contains() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(idOne, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher voucher2 = new FixedAmountVoucher(idTwo, new FixedDiscount(30), VoucherType.FIXED_AMOUNT_VOUCHER);
        VoucherResponse voucherResponse1 = new VoucherResponse(voucher1);
        VoucherResponse voucherResponse2 = new VoucherResponse(voucher2);
        List<Voucher> vouchers = List.of(voucher1, voucher2);
        Vouchers voucherRegistry = new Vouchers(vouchers);

        //when
        List<VoucherResponse> result = dtoConverter.convertVoucherResponse(voucherRegistry);

        //then
        assertThat(result).contains(voucherResponse1, voucherResponse2);

        assertThat(result)
                .usingElementComparator(DtoConverterTest::compare)
                .contains(voucherResponse1, voucherResponse2);
    }

    private static int compare(VoucherResponse a, VoucherResponse b) {
        int typeComparison = a.getVoucherType().compareTo(b.getVoucherType());
        if (typeComparison != 0) {
            return typeComparison;
        } else {
            return Double.compare(a.getDiscount().getValue(), b.getDiscount().getValue());
        }
    }

}
