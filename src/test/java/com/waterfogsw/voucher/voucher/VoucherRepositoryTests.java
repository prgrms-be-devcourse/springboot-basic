package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
public class VoucherRepositoryTests {

    @Autowired
    private VoucherRepository repository;

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("저장할 바우처의 기본키가 중복되지 않으면")
        class Context_not_duplicate_prime_key {

            @Test
            @DisplayName("성공한다")
            void it_not_throw_error() {
                final Voucher voucher1 = new FixedAmountVoucher(1L, 1000);
                final Voucher voucher2 = new FixedAmountVoucher(2L, 2000);

                final Voucher savedVoucher1 = repository.save(voucher1);
                final Voucher savedVoucher2 = repository.save(voucher2);

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
                final Voucher voucher1 = new FixedAmountVoucher(1L, 1000);
                final Voucher voucher2 = new FixedAmountVoucher(1L, 2000);

                final Voucher savedVoucher1 = repository.save(voucher1);
                final Voucher savedVoucher2 = repository.save(voucher2);

                assertThat(savedVoucher2, samePropertyValuesAs(voucher2));
            }
        }
    }
}
