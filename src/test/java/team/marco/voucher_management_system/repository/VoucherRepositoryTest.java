package team.marco.voucher_management_system.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.type_enum.VoucherType;

abstract class VoucherRepositoryTest {
    protected Voucher generateVoucher() {
        return new FixedAmountVoucher(10_000);
    }

    protected abstract VoucherRepository getRepository();

    @Test
    @DisplayName("Repository는 null일 수 없다.")
    void testNonNullRepository() {
        // given

        // when
        VoucherRepository repository = getRepository();

        // then
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("바우처 추가가 가능해야 한다.")
    void testSave() {
        // given
        VoucherRepository repository = getRepository();
        Voucher voucher = generateVoucher();

        // when
        ThrowingCallable targetMethod = () -> repository.save(voucher);

        // then
        assertThatNoException().isThrownBy(targetMethod);
    }

    @Nested
    @DisplayName("id로 바우처 삭제 테스트")
    class TestDeleteById {
        @Test
        @DisplayName("id가 일치하는 바우처가 존재할 경우 삭제할 수 있다.")
        void success() {
            // given
            VoucherRepository repository = getRepository();
            Voucher generatedVoucher = generateVoucher();
            UUID id = generatedVoucher.getId();

            repository.save(generatedVoucher);

            // when
            int count = repository.deleteById(id);

            // then
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("id가 일치하는 바우처가 없을 경우 숫자 0을 반환한다.")
        void notExist() {
            // given
            VoucherRepository repository = getRepository();
            Voucher notExistVoucher = generateVoucher();
            UUID notExistVoucherId = notExistVoucher.getId();

            // when
            int count = repository.deleteById(notExistVoucherId);

            // then
            assertThat(count).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("추가한 모든 바우처를 조회할 수 있어야 한다.")
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

        assertThat(retrievedIds).isNotEmpty();

        assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(generatedIds);
    }

    @Nested
    @DisplayName("바우처 id 조회 테스트")
    class TestFindById {
        @Test
        @DisplayName("id가 일치하는 바우처가 존재할 경우 조회할 수 있어야 한다.")
        void success() {
            // given
            VoucherRepository repository = getRepository();
            Voucher generatedVoucher = generateVoucher();
            UUID id = generatedVoucher.getId();

            repository.save(generatedVoucher);

            // when
            Optional<Voucher> optionalVoucher = repository.findById(id);

            // then
            assertThat(optionalVoucher).isNotEmpty();

            Voucher voucher = optionalVoucher.get();

            assertThat(voucher.getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("id가 일치하는 바우처 없을 경우 빈 optional 객체를 반환한다.")
        void emptyVoucher() {
            // given
            VoucherRepository repository = getRepository();
            Voucher notExistVoucher = generateVoucher();
            UUID notExistVoucherId = notExistVoucher.getId();

            // when
            Optional<Voucher> optionalVoucher = repository.findById(notExistVoucherId);

            // then
            assertThat(optionalVoucher).isEmpty();
        }
    }

    @Test
    @DisplayName("type이 일치하는 바우처를 모두 반환한다.")
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

        assertThat(counts).containsExactlyElementsOf(expectedCounts);
    }

    @Test
    @DisplayName("생성 일자를 기준으로 조회할 수 있다.")
    void testFindByCreateAt() {
        // given
        VoucherRepository repository = getRepository();
        List<Voucher> vouchers = List.of(
                generateVoucher(),
                generateVoucher(),
                generateVoucher(),
                generateVoucher(),
                generateVoucher());

        vouchers.forEach(repository::save);

        LocalDateTime from = vouchers.get(1).getCreateAt();
        LocalDateTime to = vouchers.get(3).getCreateAt();

        // when
        List<Voucher> retrievedVouchers = repository.findByCreateAt(from, to);

        // then
        List<UUID> retrievedIds = retrievedVouchers.stream()
                .map(Voucher::getId)
                .toList();
        List<UUID> expectedIds = vouchers.stream()
                .filter(voucher -> voucher.isCreatedBetween(from, to))
                .map(Voucher::getId)
                .toList();

        assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(expectedIds);
    }
}
