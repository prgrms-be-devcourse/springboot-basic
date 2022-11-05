package org.programmers.spbw1.voucher;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {
    VoucherRepository voucherRepository = new JDBCVoucherRepository();
    VoucherService voucherService = new VoucherService(voucherRepository);
    List<UUID> ids = new ArrayList<>();

    @BeforeEach
    public void generateVoucher(){
        for(int i = 0; i < 10; i++)
            ids.add(UUID.randomUUID());
        for(int i = 0; i < 5; i++)
            voucherRepository.insert(new PriceDiscountVoucher(ids.get(i), 10L));
        for(int i = 5; i < 10; i++)
            voucherRepository.insert(new PriceDiscountVoucher(ids.get(i), 10L));
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

}