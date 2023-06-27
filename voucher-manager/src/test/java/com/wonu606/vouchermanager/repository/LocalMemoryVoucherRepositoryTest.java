package com.wonu606.vouchermanager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalMemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherRepository = new LocalMemoryVoucherRepository();
    }

    @Test
    @DisplayName("save()를 이용해 저장하면 저장이 된다.")
    void testSave() {
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
    @DisplayName("저장한 바우처는 uuid 그대로 저장된다.")
    void testFindById() {
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
    @DisplayName("여러 바우처를 저장할 경우 모두 저장되어야 한다.")
    void testFindAll() {
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
    @DisplayName("존재하는 uuid를 삭제할 경우 삭제되어야 한다.")
    void testDeleteByIdWhenUuidExists() {
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
    @DisplayName("존재하지 않는 uuid를 삭제할 경우 변화가 없어야 한다.")
    void testDeleteByIdWhenUuidDoesNotExist() {
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
    @DisplayName("deleteAll()를 실행하면 저장소가 비어야 한다.")
    void testDeleteAll() {
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