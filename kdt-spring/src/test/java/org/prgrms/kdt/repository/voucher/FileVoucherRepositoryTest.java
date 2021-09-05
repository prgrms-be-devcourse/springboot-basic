package org.prgrms.kdt.repository.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.FileVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileVoucherRepositoryTest {

    @Autowired
    FileVoucherRepository fileVoucherRepository;

    @Test
    @DisplayName("바우처가 파일에 저장된다")
    public void save() throws Exception {
        // given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());

        // when
        fileVoucherRepository.insert(voucher);
        Voucher findVoucher = fileVoucherRepository.findById(voucher.getVoucherId()).get();

        // then
        Assertions.assertThat(voucher).isEqualTo(findVoucher);
    }

    @Test
    @DisplayName("파일에 저장된 모든 바우처를 가져온다")
    public void findAll() throws Exception {
        // given
        List<Voucher> all = fileVoucherRepository.findAll();
        // when

        // then
        Assertions.assertThat(all.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("파일에 저장된 특정 바우처를 가져온다")
    public void findOne() throws Exception {
        // given
        UUID id = UUID.fromString("555126fe-8a4c-4cc4-ae21-8adee087171c");
        // when
        Voucher findVoucher = fileVoucherRepository.findById(id).get();
        // then
        Assertions.assertThat(findVoucher.getVoucherId()).isEqualTo(id);
    }
}