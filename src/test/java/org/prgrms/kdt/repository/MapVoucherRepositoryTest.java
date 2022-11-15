package org.prgrms.kdt.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MapVoucherRepositoryTest {

    private VoucherRepository memory;

    @BeforeEach
    void setUp() {
        this.memory = new MapVoucherRepository();
    }

    @Test
    @DisplayName("[성공] 바우처 저장하기")
    void save() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher newVoucher = new Voucher(voucherType, discountAmount);

        boolean result = memory.save(newVoucher);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("[성공] 바우처 아이디로 조회하기")
    void getById() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher newVoucher = new Voucher(voucherType, discountAmount);
        UUID voucherId = newVoucher.getVoucherId();
        memory.save(newVoucher);

        Optional<Voucher> voucherById = memory.getVoucherById(voucherId);

        Assertions.assertEquals(voucherById.get().getVoucherId(), voucherId);
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 바우처 아이디로 조회하기")
    void getById_withInvalidId() {
        UUID invalidVoucherId = UUID.randomUUID();

        Optional<Voucher> voucherById = memory.getVoucherById(invalidVoucherId);

        Assertions.assertTrue(voucherById.isEmpty());
    }

    @Test
    @DisplayName("[성공] 모든 바우처를 가져오기")
    void getAllVoucher() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher newVoucher = new Voucher(voucherType, discountAmount);
        memory.save(newVoucher);

        List<Voucher> allVoucher = memory.getAll();

        Assertions.assertEquals(allVoucher.size(), 1);
    }
}
