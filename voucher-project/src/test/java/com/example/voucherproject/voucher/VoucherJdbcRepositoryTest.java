package com.example.voucherproject.voucher;

import com.example.voucherproject.TestRepositoryConfig;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.repository.VoucherJdbcRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.common.Helper.VoucherHelper.makeVoucher;
import static com.example.voucherproject.common.Helper.VoucherHelper.makeVoucherWithId;
import static com.example.voucherproject.voucher.model.VoucherType.FIXED;
import static com.example.voucherproject.voucher.model.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = TestRepositoryConfig.class)
class VoucherJdbcRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Nested
    @DisplayName("INSERT TEST")
    class InsertVoucherTest {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 10})
        @DisplayName("[insert:성공] 바우처 N개 정상 저장")
        void basicInsertTest(int n) {
            List<Voucher> vouchers = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                vouchers.add(voucherJdbcRepository.insert(makeVoucher(FIXED,1000L)));
            }
            var searchedVoucher = voucherJdbcRepository.findAll();
            assertThat(searchedVoucher).usingRecursiveFieldByFieldElementComparator().containsAll(vouchers);
            assertThat(searchedVoucher.size()).isEqualTo(n);
        }
    }

    @Nested
    @DisplayName("FIND TEST")
    class FindVoucherTest {
        @Test
        @DisplayName("[findAll:성공] ")
        void findAllTest() {
            var voucher1 = voucherJdbcRepository.insert(makeVoucher(FIXED,10000));
            var voucher2 = voucherJdbcRepository.insert(makeVoucher(FIXED,11000));
            var voucher3 = voucherJdbcRepository.insert(makeVoucher(FIXED,12000));

            var vouchers = voucherJdbcRepository.findAll();

            assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().contains(voucher1,voucher2,voucher3);
        }

        @Test
        @DisplayName("[findAllByVoucherType:성공] 바우처 타입으로 바우처를 검색할 수 있다.")
        void findAllByVoucherTypeTest() {
            var voucher1 = voucherJdbcRepository.insert(makeVoucher(FIXED,10000));
            var voucher2 = voucherJdbcRepository.insert(makeVoucher(PERCENT,10));
            var voucher3 = voucherJdbcRepository.insert(makeVoucher(FIXED,20000));
            var voucher4 = voucherJdbcRepository.insert(makeVoucher(PERCENT, 20));

            var vouchers = voucherJdbcRepository.findAllByVoucherType(FIXED);

            assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().contains(voucher1,voucher3);
            assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().doesNotContain(voucher2,voucher4);
        }

        @Test
        @DisplayName("[findById:성공] 바우처 ID로 바우처를 검색할 수 있다.")
        void findById() {
            UUID uuid1 = UUID.randomUUID();
            UUID uuid2 = UUID.randomUUID();
            UUID uuid3 = UUID.randomUUID();

            var voucher1 = voucherJdbcRepository.insert(makeVoucherWithId(uuid1,10000,FIXED));
            var voucher2 = voucherJdbcRepository.insert(makeVoucherWithId(uuid2,10,PERCENT));
            var voucher3 = voucherJdbcRepository.insert(makeVoucherWithId(uuid3,20000,FIXED));

            var queryVoucher1 = voucherJdbcRepository.findById(uuid1);
            var queryVoucher2 = voucherJdbcRepository.findById(uuid2);
            var queryVoucher3 = voucherJdbcRepository.findById(uuid3);

            assertThat(voucher1).usingRecursiveComparison().isEqualTo(queryVoucher1.get());
            assertThat(voucher2).usingRecursiveComparison().isEqualTo(queryVoucher2.get());
            assertThat(voucher3).usingRecursiveComparison().isEqualTo(queryVoucher3.get());

        }

    }

    @Nested
    @DisplayName("DELETE TEST")
    class DeleteVoucherTest {
        @Test
        @DisplayName("[deleteById:성공] 바우처 ID로 해당하는 바우처 삭제 가능")
        void deleteByIdTest() {
            var voucher = makeVoucher(FIXED,1000);
            voucherJdbcRepository.insert(voucher);
            voucherJdbcRepository.deleteById(voucher.getId());

            var vouchers = voucherJdbcRepository.findAll();
            assertThat(vouchers).usingRecursiveFieldByFieldElementComparator().doesNotContain(voucher);
        }
        @Test
        @DisplayName("[deleteAll:성공] 모든 바우처 삭제후 Repository 개수는 0개")
        void deleteAllTest() {
            voucherJdbcRepository.insert(makeVoucher(PERCENT,10));
            voucherJdbcRepository.insert(makeVoucher(PERCENT,10));
            voucherJdbcRepository.insert(makeVoucher(PERCENT,10));
            assertThat(voucherJdbcRepository.count()).isEqualTo(3);
            voucherJdbcRepository.deleteAll();
            assertThat(voucherJdbcRepository.count()).isEqualTo(0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2})
    @DisplayName("COUNT TEST")
    void countTest(int n){
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            vouchers.add(voucherJdbcRepository.insert(makeVoucher(FIXED, 1000)));
        }
        assertThat(voucherJdbcRepository.count()).isEqualTo(n);
    }

    @AfterEach
    void cleanUpAfterEach(){
        voucherJdbcRepository.deleteAll();
    }
    @BeforeEach
    void cleanUpBeforeEach(){
        voucherJdbcRepository.deleteAll();
    }

}
