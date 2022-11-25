package org.prgrms.kdt.dao.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import org.prgrms.kdt.model.voucher.*;
import org.prgrms.kdt.voucher.FileVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
=======
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.voucher.repository.FileVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
>>>>>>> 7b4babe (feat: 기존 애플리케이션에서 디렉토리 구조를 변경하고 바우처를 삭제하는 기능을 추가하다.)

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FileVoucherRepositoryTest {

    private static final String FIXED_TYPE = "FixedAmountVoucher";
    private static final String PERCENT_TYPE = "PercentDiscountVoucher";
    private static final VoucherRepository voucherRepository = new FileVoucherRepository(new VoucherBuilder(), "testFileReposiroty.txt");

    @BeforeEach
    void beforeEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장된 바우처를 voucherId를 이용해 찾는 기능 검증")
    void voucherId_이용한_바우처찾기() {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), "50", VoucherType.of(PERCENT_TYPE), LocalDateTime.now());

        // when
        voucherRepository.insert(voucher);
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(findVoucher.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처가 제대로 들어갔는지 repository 내부 바우처의 갯수로 검증")
    void insert() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", VoucherType.of(FIXED_TYPE), LocalDateTime.now());

        // when
        voucherRepository.insert(voucher);
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(getVoucherCount, is(1));
    }

    @Test
    @DisplayName("저장된 바우처들이 제대로 나오는지 바우처 갯수를 이용한 검증")
    void getAllStoredVoucher() {
        // given
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), "1000", VoucherType.of(FIXED_TYPE), LocalDateTime.now()));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), "2000", VoucherType.of(FIXED_TYPE), LocalDateTime.now()));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), "3000", VoucherType.of(FIXED_TYPE), LocalDateTime.now()));

        // when
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(getVoucherCount, is(3));
    }

    @Test
    @DisplayName("저장된 바우처의 customerId 값을 변경할 수 있다.")
    void 바우처_customerId_바꾸기() {
        // given
<<<<<<< HEAD
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", VoucherType.of(FIXED_TYPE), LocalDateTime.now());
=======
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", FIXED_TYPE, LocalDateTime.now());
>>>>>>> 7b4babe (feat: 기존 애플리케이션에서 디렉토리 구조를 변경하고 바우처를 삭제하는 기능을 추가하다.)
        voucherRepository.insert(voucher);

        // when
        UUID ownedCustomerId = UUID.randomUUID();
        voucher.setOwnedCustomerId(ownedCustomerId);
        Voucher updateVoucher = voucherRepository.update(voucher);

        // then
        assertThat(updateVoucher.getOwnedCustomerId().get(), is(ownedCustomerId));
    }

    @Test
    @DisplayName("저장된 바우처를 삭제할 수 있다.")
    void 바우처_삭제하기() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", FIXED_TYPE, LocalDateTime.now());
        voucherRepository.insert(voucher);

        // when
        voucherRepository.remove(voucher.getVoucherId());

        // then
        List<Voucher> allStoredVoucher = voucherRepository.getAllStoredVoucher();
        assertThat(allStoredVoucher.isEmpty(), is(true));
    }

}