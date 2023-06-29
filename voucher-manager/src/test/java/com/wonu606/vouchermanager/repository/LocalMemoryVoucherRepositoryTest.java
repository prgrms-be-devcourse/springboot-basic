package com.wonu606.vouchermanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalMemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherRepository = new LocalMemoryVoucherRepository();
    }

    @Test
    void save시_저장되어야_한다() {
        // given
        UUID generatedUuid = UUID.randomUUID();
        Voucher generatedVoucher = new PercentageVoucher(generatedUuid, 10.0d);

        // when
        voucherRepository.save(generatedVoucher);

        // then
        assertEquals(1, voucherRepository.findAll().size());
        assertTrue(voucherRepository.findById(generatedUuid).isPresent());
    }

    @Test
    void 저장한_객체는_uuid_그대로_저장된다() {
        // given
        UUID generatedUuid = UUID.randomUUID();
        Voucher generatedVoucher = new FixedAmountVoucher(generatedUuid, 5000);

        // when
        voucherRepository.save(generatedVoucher);

        Optional<Voucher> savedVoucher = voucherRepository.findById(generatedUuid);

        // then
        assertTrue(savedVoucher.isPresent());
        assertEquals(generatedVoucher.getUuid(), savedVoucher.get().getUuid());
    }

    @Test
    void 여러_바우처_저장시_모두_저장되어야_한다() {
        // given
        Voucher voucher1 = new PercentageVoucher(UUID.randomUUID(), 10.0d);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 5000);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // then
        assertEquals(2, voucherRepository.findAll().size());
    }

    @Test
    void 존재하는_uuid를_삭제할_경우_삭제되어야_한다() {
        // given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, 5000);

        // when
        voucherRepository.save(voucher);
        voucherRepository.deleteById(uuid);

        // then
        assertEquals(Optional.empty(), voucherRepository.findById(uuid));
    }

    @Test
    void 존재하지_않는_uuid_삭제시_저장소의_변화가_없어야_한다() {
        // given
        Voucher generatedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);

        // when
        int sizeBeforeDeletion = voucherRepository.findAll().size();
        voucherRepository.deleteById(UUID.randomUUID());
        int sizeAfterDeletion = voucherRepository.findAll().size();

        // then
        assertEquals(sizeBeforeDeletion, sizeAfterDeletion);
    }

    @Test
    void deleteAll시_저장소가_비어져야_한다() {
        // given
        Voucher voucher1 = new PercentageVoucher(UUID.randomUUID(), 10.0d);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 5000);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.deleteAll();

        // then
        assertEquals(0, voucherRepository.findAll().size());
    }
}
