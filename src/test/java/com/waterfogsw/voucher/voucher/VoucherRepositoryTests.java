package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
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

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @DisplayName("저장된 모든 바우처에 대한 리스트를 반환한다")
            void it_return_list() {
                Field voucherStore;

                try {
                    voucherStore = repository.getClass().getDeclaredField("voucherStore");
                    voucherStore.setAccessible(true);
                } catch (NoSuchFieldException testException) {
                    throw new RuntimeException(testException.getMessage());
                }

                try {
                    Map<Long, Voucher> map = (Map<Long, Voucher>) voucherStore.get(repository);

                    final Voucher voucher1 = new FixedAmountVoucher(1L, 1000);
                    final Voucher voucher2 = new FixedAmountVoucher(2L, 1000);

                    map.put(voucher1.getId(), voucher1);
                    map.put(voucher2.getId(), voucher2);

                    final List<Voucher> voucherList = repository.findAll();

                    assertThat(voucherList.size(), is(2));

                } catch (IllegalAccessException testException) {
                    throw new RuntimeException(testException.getMessage());
                }
            }
        }
    }
}
