package org.prgrms.springorder.domain.voucher.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.config.VoucherFileProperties;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class VoucherFileRepositoryTest {

    private VoucherFileRepository voucherFileRepository;

    private final String path = "test/store/";
    private final String fileName = "voucher";
    private final String fileExtension = ".csv";

    @BeforeAll
    public void init() {
        VoucherFileProperties voucherFileProperties = new VoucherFileProperties(path, fileName, fileExtension);
        voucherFileRepository = new VoucherFileRepository(voucherFileProperties);
    }

    @BeforeEach
    void after() {
        voucherFileRepository.deleteAll();
    }

    @DisplayName("조회 테스트 - id로 객체를 정상적으로 찾는다.")
    @Test
    void findByIdSuccess() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(randomUUID, 50L);

        voucherFileRepository.insert(voucher);

        //when
        Optional<Voucher> optionalVoucher = voucherFileRepository.findById(randomUUID);

        //then
        assertTrue(optionalVoucher.isPresent());
        Voucher findVoucher = optionalVoucher.get();
        assertEquals(voucher, findVoucher);
        assertEquals(findVoucher.getVoucherId(), randomUUID);
    }

    @DisplayName("조회 테스트 - id로 객체를 찾지만 없으면 빈 옵셔널을 반환한다. ")
    @Test
    void findByIdReturnEmptyOptional() {
        //given
        UUID randomUUID = UUID.randomUUID();

        //when
        Optional<Voucher> optionalBlockCustomer = voucherFileRepository.findById(randomUUID);

        //then
        assertTrue(optionalBlockCustomer.isEmpty());
    }

    @DisplayName("insert 테스트 - file 에 정상 저장되고 저장된 Voucher 를 리턴한다.")
    @Test
    void insertTest() {
        //given
        UUID randomUUID = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(randomUUID, 50L);

        //when
        Voucher savedVoucher = voucherFileRepository.insert(voucher);

        //then
        Optional<Voucher> voucherOptional = voucherFileRepository.findById(randomUUID);
        assertTrue(voucherOptional.isPresent());

        Voucher findVoucher = voucherOptional.get();

        assertNotNull(findVoucher);
        assertEquals(voucher, savedVoucher);
        assertEquals(savedVoucher, findVoucher);
        assertNull(voucher.getCustomerId());
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given
        int saveCount = 5;
        IntStream.range(0, saveCount).forEach(i -> {
            Voucher voucher;

            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
            }

            voucherFileRepository.insert(voucher);
        });

        //when
        List<Voucher> vouchers = voucherFileRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertEquals(saveCount, vouchers.size());
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<Voucher> vouchers = voucherFileRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type과 amount가 바뀐다. ")
    @Test
    void updateVoucherTypeAndAmount() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherFileRepository.insert(voucher);

        long updateAmount = 10L;
        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherFileRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherFileRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());

    }

    @DisplayName("update 테스트 - 저장된 Voucher의 amount가 바뀐다. ")
    @Test
    void updateVoucherAmount() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherFileRepository.insert(voucher);

        long updateAmount = 10L;
        Voucher updateVoucher = new FixedAmountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherFileRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherFileRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateAmount, findUpdatedVoucher.getAmount());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type이 바뀐다. ")
    @Test
    void updateVoucherType() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherFileRepository.insert(voucher);

        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, amount);

        //when
        Voucher updatedVoucher = voucherFileRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherFileRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());
    }

    @DisplayName("findByIdWithCustomer Join 테스트 - 메모리 레포지토리를 지원하지 않으므로 예외를 던진다.")
    @Test
    void findByIdWithCustomerThrowException() {
        //given
        UUID voucherId = UUID.randomUUID();
        //when & then
        assertThrows(RuntimeException.class,
            () -> voucherFileRepository.findByIdWithCustomer(voucherId));
    }

    @DisplayName("deleteById 테스트 - voucherId로 저장된 Voucher가 제거된다.")
    @Test
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherFileRepository.insert(voucher);

        //when
        voucherFileRepository.deleteById(voucherId);

        //then
        Optional<Voucher> voucherOptional = voucherFileRepository.findById(voucherId);

        assertTrue(voucherOptional.isEmpty());
    }

}