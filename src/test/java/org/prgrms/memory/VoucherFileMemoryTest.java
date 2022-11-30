package org.prgrms.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;

class VoucherFileMemoryTest {

    private VoucherFileMemory voucherFileMemory;
    private final String filePath = "src/test/resources/voucher_memory_test.csv";

    @BeforeEach
    void setUp() {
        voucherFileMemory = new VoucherFileMemory(filePath);
    }

    @DisplayName("파일 내용을 모두 삭제해준다")
    @BeforeEach
    void truncateFile() {
        try (PrintWriter pw = new PrintWriter(filePath)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("csv파일에 voucher정보를 저장 후 저장한 voucher타입을 리턴한다")
    @Test
    void saveTest() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L),
            LocalDateTime.now());
        //when
        Voucher saved = voucherFileMemory.save(voucher);
        //then
        assertEquals(voucher, saved);
    }

    @DisplayName("저장된 모든 바우처의 정보를 리턴한다.")
    @Test
    void findAllTest() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L),
            LocalDateTime.now().withNano(0));
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(100L),
            LocalDateTime.now().withNano(0));
        voucherFileMemory.save(voucher1);
        voucherFileMemory.save(voucher2);
        //when
        List<Voucher> voucherList = voucherFileMemory.findAll();
        //then
        assertEquals(voucherList.size(), 2);
        assertThat(voucherList).contains(voucher1, voucher2);
    }

}