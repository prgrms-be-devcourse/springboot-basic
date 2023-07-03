package org.devcourse.voucher.repository;

import org.devcourse.voucher.domain.voucher.Voucher;
import org.devcourse.voucher.domain.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryVoucherRepositoryTest {


    @Test
    @DisplayName("바우처 저장 요청 시 아이디가 추가되어 저장된다")
    void saveVoucherWithId() {
        //given
        VoucherRepository repository = new InMemoryVoucherRepository();
        Voucher voucher = new Voucher(0, VoucherType.FIX, 1000);

        //when
        Voucher savedVoucher = repository.save(voucher);

        //then
        assertThat(savedVoucher.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처 저장에 실패한다 - null 입력")
    public void failSaveVoucherWithNull () {
        VoucherRepository repository = new InMemoryVoucherRepository();

        assertThatThrownBy(() -> repository.save(null))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("빈 값을 저장 할 수 없습니다");
    }

    @Test
    @DisplayName("모든 바우처를 불러온다")
    void findAllVouchers() {
        VoucherRepository repository = new InMemoryVoucherRepository();
        Voucher voucher = new Voucher(0, VoucherType.FIX, 1000);
        repository.save(voucher);
        repository.save(voucher);
        repository.save(voucher);

        List<Voucher> allVouchers = repository.findAll();

        assertThat(allVouchers.size()).isEqualTo(3);
    }

}
