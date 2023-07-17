package com.prgrms.service.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.PercentDiscountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import com.prgrms.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class VoucherServiceTest {

    private final static int FIXED_ID = 2;
    private final static int PERCENT_ID = 40;
    private final static double DISCOUNT_AMOUNT = 20;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherService voucherService;

    @Test
    @DisplayName("만들고자 하는 바우처를 createVoucher()로 만들었을 때 기대값과 같은 바우처를 반환한다.")
    void createVoucher_RepositoryInsertVoucher_Equals() {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(DISCOUNT_AMOUNT);
        Voucher createdVoucher = new FixedAmountVoucher(FIXED_ID, discount, voucherType);

        //when
        Voucher result = voucherService.createVoucher(FIXED_ID, voucherType, DISCOUNT_AMOUNT);

        //then
        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(createdVoucher);
    }

    @ParameterizedTest
    @DisplayName("몇 개의 바우처 정책을 List로 만들어 저장소에 저장한 결과와 getAllVocherList의 결과는 같다.")
    @MethodSource("voucherProvider")
    void getAllVoucherList_RepositoryListVoucherList_Equals(List<Voucher> list) {
        // Given
        Voucher voucher1=  voucherRepository.insert(list.get(0));
        Voucher voucher2 = voucherRepository.insert(list.get(1));
        VoucherResponse voucherResponse1 = new VoucherResponse(voucher1);
        VoucherResponse voucherResponse2 = new VoucherResponse(voucher2);

        // When
        List<VoucherResponse> result = voucherService.getAllVoucherList();

        // Then
        assertThat(result)
                .containsOnly(voucherResponse1, voucherResponse2);

    }

    private static Stream<List<Voucher>> voucherProvider() {
        Voucher createdVoucher1 = new FixedAmountVoucher(FIXED_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(PERCENT_ID, new PercentDiscount(20),
                VoucherType.PERCENT_DISCOUNT_VOUCHER);
        return Stream.of(
                List.of(createdVoucher1, createdVoucher2)
        );
    }

}
