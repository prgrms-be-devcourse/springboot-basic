package com.programmers.springvoucherservice.repository;

import com.programmers.springvoucherservice.VoucherFactory;
import com.programmers.springvoucherservice.domain.voucher.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springvoucherservice.domain.voucher.VoucherList.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FileVoucherRepositoryTest {

    @Autowired VoucherRepository repository;

    @Test
    void 파일에서Id로검색() {
        UUID id = UUID.fromString("2323394a-98d1-44f6-8bde-c54903e6357c");

        Optional<Voucher> result = repository.findById(id);
        Voucher voucher = result.get();

        assertThat(voucher.getVoucherId()).isEqualTo(id);
        assertThat(voucher.getValue()).isEqualTo(2000L);
    }

    @Test
    void 파일에바우처저장() {
        UUID id = UUID.randomUUID();
        Voucher voucher = VoucherFactory.createVoucher(id, FixedAmount, 2000);

        UUID saveId = repository.registerVoucher(voucher);

        assertThat(id).isEqualTo(saveId);
    }

    @Test
    void 파일의모든바우처조회() {
        List<Voucher> allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(0);

        repository.registerVoucher(VoucherFactory.createVoucher(FixedAmount, 2000));
        repository.registerVoucher(VoucherFactory.createVoucher(FixedAmount, 2000));

        allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(2);
    }
}