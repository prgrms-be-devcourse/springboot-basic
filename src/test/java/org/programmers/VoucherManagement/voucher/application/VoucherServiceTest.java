package org.programmers.VoucherManagement.voucher.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.infrastructure.MemoryVoucherRepository;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;

import java.util.UUID;


public class VoucherServiceTest {
    VoucherService voucherService;
    VoucherRepository voucherRepository;
    VoucherFactory voucherFactory;

    @BeforeEach
    void init(){
        voucherRepository = new MemoryVoucherRepository();
        voucherFactory = new VoucherFactory();
        voucherService = new VoucherService(voucherRepository,voucherFactory);
    }

    @Test
    @DisplayName("바우처정보를 이용해 저장 후 반환 - 성공")
    void 바우처정보를_저장후반환_성공(){
        //given
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(DiscountType.FIXED,2000);
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), createVoucherRequest.getDiscountType(),new DiscountValue(createVoucherRequest.getDiscountValue()));
        GetVoucherResponse getVoucherResponse = GetVoucherResponse.toDto(voucher);

        //when
        GetVoucherResponse getVoucherResponseExpect = voucherService.saveVoucher(createVoucherRequest);

        //then
        assertThat(getVoucherResponseExpect)
                .usingRecursiveComparison()
                .ignoringFields("voucherId")
                .isEqualTo(getVoucherResponse);
    }

}
