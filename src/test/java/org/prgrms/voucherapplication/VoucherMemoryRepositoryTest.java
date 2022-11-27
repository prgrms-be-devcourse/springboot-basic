package org.prgrms.voucherapplication;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.voucherapplication.domain.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.repository.VoucherMemoryRepository;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@ActiveProfiles("dev")
@DisplayName("memoryRepository 테스트")
public class VoucherMemoryRepositoryTest {

    private final VoucherMemoryRepository memoryRepository = new VoucherMemoryRepository();

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("바우처를 메모리에 저장한 뒤 조회하면")
    class save {

        Stream<Arguments> createVouchers() {
            FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());
            PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 90, LocalDateTime.now());

            return Stream.of(
                    Arguments.of(fixedAmountVoucher, 1),
                    Arguments.of(percentDiscountVoucher, 2)
            );
        }

        @ParameterizedTest
        @MethodSource("createVouchers")
        @DisplayName("메모리에 해당 Voucher 객체가 저장되고, 전체 size가 +1 된다.")
        void listAdd(Voucher voucher, int size) {
            memoryRepository.save(voucher);

            List<Voucher> voucherList = memoryRepository.findAll();

            MatcherAssert.assertThat(voucherList, Matchers.hasSize(size));
            MatcherAssert.assertThat(voucherList, Matchers.hasItem(voucher));
        }
    }
}
