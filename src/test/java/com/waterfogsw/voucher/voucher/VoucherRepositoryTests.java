package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class VoucherRepositoryTests {

    @Autowired
    private VoucherRepository repository;

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        Long key1 = 1L;
        Long key2 = 2L;

        final Voucher voucher1 = new Voucher(key1, VoucherType.FIXED_AMOUNT, 1000);
        final Voucher voucher1_copy = new Voucher(key2, VoucherType.FIXED_AMOUNT, 2000);
        final Voucher voucher2 = new Voucher(key2, VoucherType.FIXED_AMOUNT, 1000);

        @Nested
        @DisplayName("저장할 바우처의 기본키가 중복되지 않으면")
        class Context_not_duplicate_prime_key {

            @Test
            @DisplayName("성공한다")
            void it_not_throw_error() {
                Voucher savedVoucher1 = repository.save(voucher1);
                Voucher savedVoucher2 = repository.save(voucher2);

                assertThat(savedVoucher1, samePropertyValuesAs(voucher1));
                assertThat(savedVoucher2, samePropertyValuesAs(voucher2));
            }
        }

        @Nested
        @DisplayName("저장할 바우처의 기본키가 중복되면")
        class Context_duplicate_prime_key {

            @Test
            @DisplayName("데이터가 업데이트 된다")
            void it_not_throw_error() {
                Voucher savedVoucher1 = repository.save(voucher1);
                Voucher savedVoucher2 = repository.save(voucher1_copy);

                assertThat(savedVoucher2, samePropertyValuesAs(voucher1_copy));
            }
        }

    }
}
