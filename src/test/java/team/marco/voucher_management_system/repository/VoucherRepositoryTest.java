package team.marco.voucher_management_system.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.Voucher;

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
    @DisplayName("Voucher 추가가 가능해야한다.")
    void testSave() {
        // given
        VoucherRepository repository = getRepository();
        Voucher voucher = generateVoucher();

        // when
        repository.save(voucher);

        // then
    }

    @Test
    @DisplayName("추가한 모든 Voucher를 조회할 수 있어야한다.")
    void testFindAll() {
        // given
        VoucherRepository repository = getRepository();
        List<Voucher> generatedVouchers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            generatedVouchers.add(generateVoucher());
        }

        generatedVouchers.sort(Comparator.comparing(Voucher::getId));

        // when
        generatedVouchers.forEach(repository::save);

        // then
        List<Voucher> retrievedVouchers = repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Voucher::getId))
                .toList();

        assertThat(retrievedVouchers, not(empty()));

        Assertions.assertThat(retrievedVouchers).usingRecursiveComparison()
                .isEqualTo(generatedVouchers);
    }
}
