package org.prgrms.weeklymission.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.weeklymission.voucher.domain.PercentDiscountVoucher;
import org.prgrms.weeklymission.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("local")
class MemoryVoucherRepositoryTest {
    private final VoucherRepository repo;
    private final Voucher voucher = new PercentDiscountVoucher(randomUUID(), 10);

    @Autowired
    MemoryVoucherRepositoryTest(VoucherRepository repo) {
        this.repo = repo;
    }

    @AfterEach
    void afterEach() {
        repo.clear();
    }

    @Test
    @DisplayName("메모리 바우처 레포 저장 테스트")
    void test_mem_save() {
        repo.save(voucher);

        assertThat(repo.getStorageSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("메모리 바우처 레포 바우처 찾기 & 찾지 못할 경우")
    void test_mem_find() {
        repo.save(voucher);

        UUID voucherId = voucher.getVoucherId();

        Voucher findVoucher = repo.findById(voucherId).get();

        assertThat(findVoucher).isNotNull();
        assertThat(findVoucher.getVoucherId()).isEqualTo(voucherId);

        UUID randomId = randomUUID();

        assertThrows(NoSuchElementException.class, () -> repo.findById(randomId).get());
    }

    @Test
    @DisplayName("메모리 바우처 레포 모든 바우처 찾기")
    void test_mem_all() {
        repo.save(voucher);

        var voucher2 = new PercentDiscountVoucher(randomUUID(), 10);
        var voucher3 = new PercentDiscountVoucher(randomUUID(), 10);
        var voucher4 = new PercentDiscountVoucher(randomUUID(), 10);
        var voucher5 = new PercentDiscountVoucher(randomUUID(), 10);

        repo.save(voucher2);
        repo.save(voucher3);
        repo.save(voucher4);
        repo.save(voucher5);

        assertThat(repo.findAll()).isNotNull();
        assertThat(repo.getStorageSize()).isEqualTo(5);
    }
}