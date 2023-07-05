package com.programmers.repository.voucher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class FileVoucherRepositoryTest {

    String filePath = "src/test/resources/voucher_file.csv";
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(filePath);

    @DisplayName("파일에 기록된 바우처를 조회한다")
    @Test
    void findAll() {
        //given
        Voucher expected = new FixedAmountVoucher(UUID.fromString("e876b46c-c5d9-4ee7-83b6-779aa2416a47"), "Fixed1", 2L);

        //when
        List<Voucher> result = fileVoucherRepository.findAll();

        //then
        assertThat(result.get(0).getVoucherId(), is(expected.getVoucherId()));
    }

    @DisplayName("바우처 정보를 추출한다")
    @Test
    void extractVoucher() {
        //given
        String line = "[ Voucher Type = Fixed Amount Voucher, Id = e876b46c-c5d9-4ee7-83b6-779aa2416a47, discount amount = 2, voucher name = Fixed1 ]";
        Voucher expected = new FixedAmountVoucher(UUID.fromString("e876b46c-c5d9-4ee7-83b6-779aa2416a47"), "Fixed1", 2L);

        //when
        Voucher result = fileVoucherRepository.extractVoucher(line);

        //then
        assertThat(result.getVoucherId(), is(expected.getVoucherId()));
    }
}