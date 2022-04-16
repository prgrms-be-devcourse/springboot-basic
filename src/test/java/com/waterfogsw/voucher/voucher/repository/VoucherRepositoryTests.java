package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class VoucherRepositoryTests {

    private VoucherRepository repository;

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        String key1 = "550e8400-e29b-41d4-a716-446655440000";
        String key2 = "550e8400-e29b-41d4-a716-446655441234";

        final Voucher voucher1 = new Voucher(UUID.fromString(key1), VoucherType.FIXED_AMOUNT, 1000d);
        final Voucher voucher1_copy = new Voucher(UUID.fromString(key1), VoucherType.FIXED_AMOUNT, 1000d);
        final Voucher voucher2 = new Voucher(UUID.fromString(key2), VoucherType.FIXED_AMOUNT, 1000d);

        @DisplayName("중복되는 기본키가 저장되지 않으면")
        class Context_not_duplicate_prime_key {

            @Test
            @DisplayName("성공한다")
            void it_not_throw_error() {
                Voucher savedVoucher1 = repository.save(voucher1);
                Voucher savedVoucher2 = repository.save(voucher2);

                assertThat(voucher1, samePropertyValuesAs(voucher1));
                assertThat(voucher2, samePropertyValuesAs(voucher2));
            }
        }

        @DisplayName("중복되는 기본키가 저장되면")
        class Context_duplicate_prime_key {

            @Test
            @DisplayName("실패한다")
            void it_not_throw_error() {
                repository.save(voucher1);
                assertThrows(IllegalArgumentException.class, () -> repository.save(voucher1_copy));
            }
        }

    }


    @Test
    @DisplayName("바우처 저장 - 저장되면 성공")
    public void saveVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);

        // when
        Voucher savedVoucher = repository.save(voucher);

        // then
        assertEquals(voucher, savedVoucher);
    }


    @Test
    @DisplayName("바우처 조회 - 조회되면 성공")
    public void findVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        repository.save(voucher);

        // when
        var find = repository.findById(voucher.getId());

        // then
        assertThat(find.isEmpty(), is(false));
        assertEquals(voucher, find.get());
    }

    @Test
    @DisplayName("바우처 전체 조회 - 모두 조회되면 성공")
    public void findAllVoucher() throws Exception {
        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);

        repository.save(voucher1);
        repository.save(voucher2);
        repository.save(voucher3);

        // when
        var findVouchers = repository.findAll();

        // then
        assertEquals(findVouchers, containsInAnyOrder(voucher1, voucher2, voucher3));
    }
}
