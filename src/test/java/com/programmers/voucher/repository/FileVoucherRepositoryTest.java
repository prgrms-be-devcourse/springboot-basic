package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherList.FixedAmount;
import static com.programmers.voucher.voucher.VoucherList.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {

    @Autowired
    FileVoucherRepository repository;

    @BeforeEach
    void setting() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("파일에 저장한 바우처를 id로 검색한다.")
    void 파일에서Id로검색() {
        UUID id = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(id, PercentDiscount, 6);
        repository.registerVoucher(voucher);

        Optional<Voucher> result = repository.findById(id);
        Voucher findOne = result.get();

        assertThat(findOne.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(findOne.getValue()).isEqualTo(voucher.getValue());
    }

    @Test
    @DisplayName("저장 전 바우처의 id와 저장 후 바우처가 반환하는 id가 동일해야 한다.")
    void 파일에바우처저장() {
        UUID id = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(id, FixedAmount, 2000);

        UUID saveId = repository.registerVoucher(voucher).getVoucherId();

        assertThat(id).isEqualTo(saveId);
    }

    @Test
    @DisplayName("파일에 바우처를 저장한 뒤 전체 조회했을 때 결과의 size가 저장 횟수와 일치해야 한다.")
    void 파일의모든바우처조회() {
        List<Voucher> allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(0);

        repository.registerVoucher(VoucherFactory.createVoucher(FixedAmount, 2000));
        repository.registerVoucher(VoucherFactory.createVoucher(FixedAmount, 2000));

        allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(2);
    }

}
