package org.prgrms.springbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.voucher.Voucher;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository repository = new MemoryVoucherRepository();
    Voucher voucher = Voucher.fixedVoucher(randomUUID(), 10, randomUUID());

    @BeforeEach
    void init() {
        repository.clear();
    }

    @Test
    @DisplayName("바우처를 저장하면 레포지토리의 사이즈가 증가해야한다.")
    void save() {
        repository.save(voucher);

        assertThat(repository.countStorageSize(), is(1));
    }

    @Test
    @DisplayName("저장한 바우처 아이디로 찾은 객체는 비어있지 않고 저장한 객체와 같아야 한다.")
    void findById() {
        repository.save(voucher);
        var voucherId = voucher.getVoucherId();

        var foundVoucher = repository.findById(voucherId);

        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), is(voucher));
    }

    @Test
    @DisplayName("바우처를 저장 후 모두 조회를 하면 저장한 개수와 리스트의 사이즈가 같아야 하고 리스트는 저장한 바우처를 모두 포함해야한다.")
    void findAll() {
        Voucher newVoucher1 = Voucher.fixedVoucher(randomUUID(), 10, randomUUID());
        Voucher newVoucher2 = Voucher.fixedVoucher(randomUUID(), 20, randomUUID());
        Voucher newVoucher3 = Voucher.fixedVoucher(randomUUID(), 30, randomUUID());
        Voucher newVoucher4 = Voucher.fixedVoucher(randomUUID(), 40, randomUUID());
        Voucher newVoucher5 = Voucher.fixedVoucher(randomUUID(), 50, randomUUID());

        repository.save(newVoucher1);
        repository.save(newVoucher2);
        repository.save(newVoucher3);
        repository.save(newVoucher4);
        repository.save(newVoucher5);

        var vouchers = repository.findAll();

        assertThat(vouchers.size(), is(5));
        assertThat(vouchers, containsInAnyOrder(newVoucher1, newVoucher2, newVoucher3, newVoucher4, newVoucher5));
    }

    @Test
    @DisplayName("레포지토리를 클리어하면 레포지토리 사이즈가 0이되어야 한다.")
    void clear() {
        repository.save(voucher);
        assertThat(repository.countStorageSize(), is(1));

        repository.clear();
        assertThat(repository.countStorageSize(), is(0));
    }
}