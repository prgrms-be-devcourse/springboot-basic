package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import org.ini4j.Wini;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {

    @Autowired
    ApplicationContext ac;

    @Autowired
    Wini wini;

    @Autowired
    FileVoucherRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("실행중에 저장한 바우처는 dumper에서 검색할 수 있어야 한다.")
    void 조회테스트() {
        Voucher voucher = PercentDiscount.createVoucher(UUID.randomUUID(), 5);
        repository.registerVoucher(voucher);

        Optional<Voucher> result = repository.getDumper().stream()
                .filter(vouchers -> vouchers.equals(voucher))
                .findFirst();

        assertTrue(result.isPresent());

        Voucher findOne = result.get();

        assertEquals(voucher, findOne);
    }

    @Test
    @DisplayName("실행전 파일에 저장된 바우처의 개수와 loadStore의 size는 동일해야 한다.")
    void 조회테스트2() {
        Set<String> voucherList = wini.keySet();
        List<Voucher> loadStore = repository.getLoadStore();

        assertEquals(voucherList.size(), loadStore.size());
    }

    @Test
    @DisplayName("실행전 파일에 저장된 바우처의 Id는 loadStore에 저장된 바우처의 Id와 일치해야 한다.")
    void 조회테스트3() {
        Set<String> voucherSet = wini.keySet();
        List<Voucher> loadStore = repository.getLoadStore();

        List<String> idList = loadStore.stream()
                .map(vouchers -> vouchers.getVoucherId().toString())
                .collect(Collectors.toList());

        voucherSet.stream().forEach(id -> {
            assertThat(idList).contains(id);
        });
    }


    @Test
    @DisplayName("파일에 저장한 바우처를 id로 검색한다.")
    void 파일에서Id로검색() {

        UUID id = UUID.randomUUID();
        Voucher voucher = PercentDiscount.createVoucher(id, 6);
        repository.registerVoucher(voucher);

        Optional<Voucher> result = repository.findById(id);
        assertTrue(result.isPresent());

        Voucher findOne = result.get();

        assertThat(findOne).isEqualTo(voucher);
    }

    @Test
    @DisplayName("저장 전 바우처의 id와 저장 후 바우처가 반환하는 id가 동일해야 한다.")
    void 파일에바우처저장() {
        UUID id = UUID.randomUUID();
        Voucher voucher = FixedAmount.createVoucher(id, 2000);

        UUID saveId = repository.registerVoucher(voucher).getVoucherId();
        assertThat(saveId).isEqualTo(id);
    }

    @Test
    @DisplayName("파일에 바우처를 저장한 뒤 전체 조회했을 때 결과의 size가 저장 횟수와 일치해야 한다.")
    void 파일의모든바우처조회() {
        repository.deleteAll();
        List<Voucher> allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(0);

        Voucher voucher1 = FixedAmount.createVoucher(5000);
        Voucher voucher2 = FixedAmount.createVoucher(8000);

        repository.registerVoucher(voucher1);
        repository.registerVoucher(voucher2);

        allVouchers = repository.findAllVouchers();
        assertThat(allVouchers.size()).isEqualTo(2);
        assertThat(allVouchers).contains(voucher1, voucher2);

    }
}
