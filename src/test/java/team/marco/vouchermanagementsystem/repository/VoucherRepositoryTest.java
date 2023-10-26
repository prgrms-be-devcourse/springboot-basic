package team.marco.vouchermanagementsystem.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;

abstract class VoucherRepositoryTest {
    protected abstract VoucherRepository getRepository();

    protected Voucher generateVoucher() {
        return new FixedAmountVoucher(10_000);
    }

    @Test
    @DisplayName("Repository 반환 테스트")
    void name() {
        // given

        // when
        VoucherRepository repository = getRepository();

        // then
        assertThat(repository, notNullValue());
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

        // when
        generatedVouchers.forEach(repository::save);

        // then
        List<Voucher> retrieveVouchers = repository.findAll();

        assertThat(retrieveVouchers, not(empty()));
        assertThat(generatedVouchers, everyItem(is(in(retrieveVouchers))));
    }
}
