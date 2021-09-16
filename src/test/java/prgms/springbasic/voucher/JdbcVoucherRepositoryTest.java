package prgms.springbasic.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void save() {
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        UUID newVoucherId = newVoucher.getVoucherId();
        try {
            voucherRepository.save(newVoucher);
        } catch (IOException exception) {
            System.out.println("바우처를 생성하지 못했습니다.");
        }
        try {
            Assertions.assertThat(voucherRepository.findById(newVoucherId).get().getDiscountValue())
                    .isEqualTo(newVoucher.getDiscountValue());
        } catch (IOException exception) {
            System.out.println("바우처 레포지토리를 읽지 못했습니다.");
        }
    }
}