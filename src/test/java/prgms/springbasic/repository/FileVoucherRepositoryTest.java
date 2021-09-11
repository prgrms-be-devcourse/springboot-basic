package prgms.springbasic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prgms.springbasic.voucher.FixedAmountVoucher;
import prgms.springbasic.voucher.Voucher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class FileVoucherRepositoryTest {
    @BeforeEach
    void removeFile() {
        Path path = Path.of("src/main/VoucherData.csv");
        try {
            Files.deleteIfExists(path);
        } catch (IOException exception) {
            System.err.println("파일 지우기에 실패했습니다.");
        }
    }

    @Test
    void getVoucherList() {
        FileVoucherRepository repository = new FileVoucherRepository();
        List<Voucher> voucherList = repository.getVoucherList();
        assertThat(voucherList).isEmpty();
    }

    @Test
    void findById() {
        FileVoucherRepository repository = new FileVoucherRepository();
        FixedAmountVoucher voucher01 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        FixedAmountVoucher voucher02 = new FixedAmountVoucher(UUID.randomUUID(), 2000);

        repository.save(voucher01);
        repository.save(voucher02);

        Voucher foundVoucher = repository.findById(voucher01.getVoucherId()).get();
        assertThat(foundVoucher.getDiscountValue()).isEqualTo(voucher01.getDiscountValue());
    }
}