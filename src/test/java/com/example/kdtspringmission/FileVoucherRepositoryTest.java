package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.repository.FileVoucherRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileVoucherRepositoryTest {

    private FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();

    @AfterEach
    void afterEach() {
        fileVoucherRepository.clear();
    }

    @Test
    void testInsert() {

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 500L);
        fileVoucherRepository.insert(voucher);
        Voucher findVoucher = fileVoucherRepository.findById(voucher.getId());
        assertThat(voucher).isEqualTo(findVoucher);

    }

    @Test
    void testFindByIdAssertAmount() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 500L);
        fileVoucherRepository.insert(voucher);
        FixedAmountVoucher findVoucher = (FixedAmountVoucher) fileVoucherRepository.findById(voucher.getId());
        assertThat(voucher.getAmount()).isEqualTo(findVoucher.getAmount());
    }

    @Test
    void testFindByIdAssertPercent() {
        RateAmountVoucher voucher = new RateAmountVoucher(UUID.randomUUID(), 20);
        fileVoucherRepository.insert(voucher);
        RateAmountVoucher findVoucher = (RateAmountVoucher) fileVoucherRepository.findById(voucher.getId());
        assertThat(voucher.getPercent()).isEqualTo(findVoucher.getPercent());
    }

    @Test
    void testFindAll() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 500L);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 300L);
        Voucher voucher3 = new RateAmountVoucher(UUID.randomUUID(), 30L);

        fileVoucherRepository.insert(voucher1);
        fileVoucherRepository.insert(voucher2);
        fileVoucherRepository.insert(voucher3);

        assertThat(fileVoucherRepository.findAll().size()).isEqualTo(3);

    }

}
