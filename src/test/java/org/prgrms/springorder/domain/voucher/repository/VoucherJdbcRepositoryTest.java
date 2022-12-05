package org.prgrms.springorder.domain.voucher.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springorder.common.JdbcTestBase;
import org.prgrms.springorder.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@TestInstance(Lifecycle.PER_CLASS)
public class VoucherJdbcRepositoryTest extends JdbcTestBase {

    private VoucherJdbcRepository voucherJdbcRepository;

    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeAll
    void setup() {
        voucherJdbcRepository = new VoucherJdbcRepository(jdbcTemplate);
        customerRepository = new CustomerJdbcRepository(jdbcTemplate);
    }

    @DisplayName("조회 테스트 - 저장된 바우처가 없다면 빈 옵셔널을 반환한다.")
    @Test
    void findByIdReturnEmpty() {
        //given & when
        UUID randomUUID = UUID.randomUUID();
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(randomUUID);
        //then
        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("저장 테스트 -  PK로 중복된 값을 삽입하면 예외가 발생한다. ")
    @Test
    void insert() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 100);
        Voucher duplicateVoucher = new FixedAmountVoucher(voucherId, 100);

        voucherJdbcRepository.insert(voucher);
        //when & then
        assertThrows(DuplicateKeyException.class, () -> voucherJdbcRepository.insert(duplicateVoucher));
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 모두 리턴된다.")
    @Test
    void findAllTest() {
        //given
        int saveCount = 5;

        List<Voucher> vouchers = createVouchers(saveCount);

        vouchers.forEach(voucher -> voucherJdbcRepository.insert(voucher));

        //when
        List<Voucher> findVouchers = voucherJdbcRepository.findAll();

        //then
        assertNotNull(vouchers);
        assertEquals(saveCount, vouchers.size());
        assertThat(findVouchers).isEqualTo(vouchers);
    }

    @DisplayName("findAll 테스트 - 저장된 Voucher 가 없다면 빈 리스트가 반환된다.")
    @Test
    void findAllReturnEmptyListTest() {
        //given & when
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        //then
        assertNotNull(vouchers);
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("update 테스트 - 저장된 Voucher의 Type, amount 가 바뀐다. ")
    @Test
    void updateVoucherFailThrowsExceptionTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

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
        voucherJdbcRepository.insert(voucher);

        long updateAmount = 10L;
        Voucher updateVoucher = new FixedAmountVoucher(voucherId, updateAmount);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

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
        voucherJdbcRepository.insert(voucher);

        VoucherType updateVoucherType = VoucherType.PERCENT;
        Voucher updateVoucher = new PercentDiscountVoucher(voucherId, amount);

        //when
        Voucher updatedVoucher = voucherJdbcRepository.update(updateVoucher);

        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        //then
        assertEquals(updatedVoucher, updatedVoucher);
        assertTrue(voucherOptional.isPresent());
        Voucher findUpdatedVoucher = voucherOptional.get();
        assertEquals(updatedVoucher, findUpdatedVoucher);

        assertEquals(voucherId, findUpdatedVoucher.getVoucherId());
        assertEquals(updateVoucherType, findUpdatedVoucher.getVoucherType());
    }

    @DisplayName("deleteById 테스트 - voucherId로 저장된 Voucher가 제거된다.")
    @Test
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherJdbcRepository.insert(voucher);

        //when
        voucherJdbcRepository.deleteById(voucherId);

        //then
        Optional<Voucher> voucherOptional = voucherJdbcRepository.findById(voucherId);

        assertTrue(voucherOptional.isEmpty());
    }

    @DisplayName("deleteAll 테스트 - 저장된 모든 voucher 가 삭제된다.")
    @Test
    void deleteAllTest() {
        //given
        createVouchers(3).forEach(voucher -> voucherJdbcRepository.insert(voucher));
        //when
        voucherJdbcRepository.deleteAll();

        List<Voucher> vouchers = voucherJdbcRepository.findAll();

        //then
        assertTrue(vouchers.isEmpty());
    }

    private List<Voucher> createVouchers(int saveCount) {
        return IntStream.range(0, saveCount).mapToObj(i -> {

            if (i % 2 == 0) {
                return new PercentDiscountVoucher(UUID.randomUUID(), i);
            }
            return new FixedAmountVoucher(UUID.randomUUID(), i);

        }).collect(Collectors.toList());
    }

}
