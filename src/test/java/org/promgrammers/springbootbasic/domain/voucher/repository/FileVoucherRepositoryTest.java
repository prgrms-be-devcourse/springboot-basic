package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.FileVoucherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

    private FileVoucherRepository fileVoucherRepository;

    @BeforeAll
    void setUp() {
        String filePath = "src/main/resources/storage/voucherStorage.txt";
        fileVoucherRepository = new FileVoucherRepository(filePath);
    }

    @AfterEach
    void tearDown() {
        fileVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("파일로 저장 성공")
    void saveFileSuccessTest() throws Exception {

        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        //when
        Voucher savedVoucher = fileVoucherRepository.insert(voucher);

        //then
        assertThat(savedVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("파일로부터 조회 성공")
    void findAllFromFileSuccessTest() throws Exception {

        // given
        List<Voucher> expectedVouchers = new ArrayList<>();
        expectedVouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 100));
        expectedVouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 10));

        for (Voucher expectedVoucher : expectedVouchers) {
            fileVoucherRepository.insert(expectedVoucher);
        }

        //when
        List<Voucher> allVouchers = fileVoucherRepository.findAll();

        //then
        assertEquals(expectedVouchers.size(), allVouchers.size());
        assertThat(allVouchers.get(0).getVoucherId()).isEqualTo(expectedVouchers.get(0).getVoucherId());
        assertThat(allVouchers.get(1).getVoucherId()).isEqualTo(expectedVouchers.get(1).getVoucherId());
    }
}