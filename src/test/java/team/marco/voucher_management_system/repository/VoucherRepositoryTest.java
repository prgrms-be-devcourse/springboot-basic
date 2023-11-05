package team.marco.voucher_management_system.repository;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.type_enum.VoucherType;

abstract class VoucherRepositoryTest {
    protected abstract VoucherRepository getRepository();

    protected Voucher generateVoucher() {
        return new FixedAmountVoucher(10_000);
    }

    @Test
    @DisplayName("Repository는 null일 수 없다.")
    void testNonNullRepository() {
        // given

        // when
        VoucherRepository repository = getRepository();

        // then
        assertThat(repository, notNullValue());
    }

    @Test
    @DisplayName("Voucher 추가가 가능해야 한다.")
    void testSave() {
        // given
        VoucherRepository repository = getRepository();
        Voucher voucher = generateVoucher();

        // when
        ThrowingCallable targetMethod = () -> repository.save(voucher);

        // then
        assertThatNoException().isThrownBy(targetMethod);
    }

    @Test
    @DisplayName("추가한 모든 Voucher를 조회할 수 있어야 한다.")
    void testFindAll() {
        // given
        VoucherRepository repository = getRepository();
        List<Voucher> generatedVouchers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            generatedVouchers.add(generateVoucher());
        }

        // when
        generatedVouchers.forEach(repository::save);

        // then
        List<UUID> generatedIds = generatedVouchers.stream()
                .map(Voucher::getId)
                .toList();
        List<UUID> retrievedIds = repository.findAll()
                .stream()
                .map(Voucher::getId)
                .toList();

        assertThat(retrievedIds, not(empty()));

        Assertions.assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(generatedIds);
    }

    @Nested
    @DisplayName("Voucher id 조회 테스트")
    class TestFindById {
        @Test
        @DisplayName("id가 일치하는 Voucher가 존재할 경우 조회할 수 있어야 한다.")
        void success() {
            // given
            VoucherRepository repository = getRepository();
            Voucher generatedVoucher = generateVoucher();
            UUID id = generatedVoucher.getId();

            repository.save(generatedVoucher);

            // when
            Optional<Voucher> optionalVoucher = repository.findById(id);

            // then
            assertThat(optionalVoucher.isEmpty(), is(false));

            Voucher voucher = optionalVoucher.get();

            assertThat(voucher.getId(), is(id));
        }

        @Test
        @DisplayName("id가 일치하는 고객이 없을 경우 빈 optional 객체를 반환한다.")
        void emptyVoucher() {
            // given
            VoucherRepository repository = getRepository();
            Voucher notExistVoucher = generateVoucher();
            UUID notExistVoucherId = notExistVoucher.getId();

            // when
            Optional<Voucher> optionalVoucher = repository.findById(notExistVoucherId);

            // then
            assertThat(optionalVoucher.isEmpty(), is(true));
        }
    }

    @Test
    @DisplayName("type이 일치하는 Voucher를 모두 반환한다.")
    void testFindByType() {
        // given
        VoucherRepository repository = getRepository();
        List<Voucher> vouchers = List.of(
                generateVoucher(),
                generateVoucher(),
                generateVoucher());
        List<VoucherType> voucherTypes = Arrays.stream(VoucherType.values()).toList();

        vouchers.forEach(repository::save);

        // when
        List<Integer> counts = voucherTypes.stream()
                .map(repository::findByType)
                .map(List::size)
                .toList();

        // then
        List<Integer> expectedCounts = voucherTypes.stream()
                .map(voucherType -> vouchers.stream()
                        .filter(voucher -> voucher.isSameType(voucherType))
                        .count())
                .map(Math::toIntExact)
                .toList();

        Assertions.assertThat(counts).containsExactlyElementsOf(expectedCounts);
    }
}
