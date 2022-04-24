package org.prgrms.springbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.voucher.Voucher;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.prgrms.springbasic.domain.voucher.Voucher.fixedVoucher;

class FileVoucherRepositoryTest {

    FileVoucherRepository repository = new FileVoucherRepository();
    Voucher voucher = fixedVoucher(randomUUID(), 10);

    @BeforeEach
    void init() {
        repository.deleteVouchers();
    }

    @Test
    @DisplayName("바우처를 저장하면 레포지토리의 사이즈가 증가해야한다.")
    void save() {
        repository.save(voucher);

        assertThat(repository.countVouchers(), is(1));
    }

    @Test
    @DisplayName("저장한 바우처 아이디로 찾은 객체는 비어있지 않고 저장한 객체와 같아야 한다.")
    void findById() {
        repository.save(voucher);
        var voucherId = voucher.getVoucherId();

        Optional<Voucher> foundVoucher = repository.findByVoucherId(voucherId);

        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("바우처를 저장 후 모두 조회를 하면 저장한 개수와 리스트의 사이즈가 같아야 하고 리스트는 저장한 바우처를 모두 포함해야한다.")
    void findAll() {
        Voucher newVoucher1 = fixedVoucher(randomUUID(), 10);
        Voucher newVoucher2 = fixedVoucher(randomUUID(), 20);
        Voucher newVoucher3 = fixedVoucher(randomUUID(), 30);
        Voucher newVoucher4 = fixedVoucher(randomUUID(), 40);
        Voucher newVoucher5 = fixedVoucher(randomUUID(), 50);

        repository.save(newVoucher1);
        repository.save(newVoucher2);
        repository.save(newVoucher3);
        repository.save(newVoucher4);
        repository.save(newVoucher5);

        var vouchers = repository.findVouchers();

        assertThat(vouchers.size(), is(5));
        assertThat(vouchers.get(0), samePropertyValuesAs(newVoucher1));
    }

    @Test
    @DisplayName("레포지토리를 클리어하면 레포지토리 사이즈가 0이되어야 한다.")
    void clear() {
        repository.save(voucher);
        assertThat(repository.countVouchers(), is(1));

        repository.deleteVouchers();
        assertThat(repository.countVouchers(), is(0));
    }
}