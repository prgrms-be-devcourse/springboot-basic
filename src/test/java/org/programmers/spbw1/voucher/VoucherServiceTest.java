package org.programmers.spbw1.voucher;

import org.junit.jupiter.api.*;
import org.programmers.spbw1.voucher.Repository.MemoryVoucherRepository;
import org.programmers.spbw1.voucher.Repository.VoucherRepository;
import org.programmers.spbw1.voucher.service.VoucherService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {
    VoucherRepository voucherRepository = new MemoryVoucherRepository();
    VoucherService voucherService = new VoucherService(voucherRepository);
    List<UUID> ids = new ArrayList<>();

    @BeforeEach
    public void generateVoucher(){
        for(int i = 0; i < 2; i++)
            ids.add(UUID.randomUUID());

        voucherRepository.insert(new FixedAmountVoucher(ids.get(0), 10L));
        voucherRepository.insert(new PercentDiscountVoucher(ids.get(1), 10L));
    }
    @AfterEach
    public void cleanUp(){
        voucherRepository.clear();
        ids.clear();
    }

    @Test
    @DisplayName("VoucherService getVoucher Test")
    public void getVoucherTest(){
        for(UUID id: ids){
            assertThat(voucherService.getVoucher(id))
                    .isEqualTo(voucherRepository.getVoucherById(id).get());
        }
    }

    @Test
    @DisplayName("VoucherService discount Test")
    public void discountTest(){
        voucherRepository.showAllVouchers();

        assertThat(voucherService.useVoucher(voucherService.getVoucher(ids.get(0)), 200L))
                .isEqualTo(190L);
        assertThat(voucherService.useVoucher(voucherService.getVoucher(ids.get(1)), 200L))
                .isEqualTo(180L);
    }

}