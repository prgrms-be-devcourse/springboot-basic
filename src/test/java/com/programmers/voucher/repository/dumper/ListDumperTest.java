package com.programmers.voucher.repository.dumper;

import com.programmers.voucher.voucher.Voucher;
import org.ini4j.Wini;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherFactory.createVoucher;
import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Profile("test")
class ListDumperTest {

    @Autowired
    Wini wini;

    @Autowired
    Dumper dumper;


    @Test
    @DisplayName("덤프 후 파일에 저장된 바우처의 개수는 메모리에 저장된 개수만큼 증가해야 한다.")
    void dumpTest() {
        int beforeDumpSize = wini.entrySet().size();

        Map<UUID, Voucher> cacheMap = new HashMap<>();
        cacheMap.put(UUID.randomUUID(), createVoucher(FixedAmount, 5000));
        cacheMap.put(UUID.randomUUID(), createVoucher(FixedAmount, 9000));

        dumper.dump(cacheMap);
        int afterDumpSize = wini.entrySet().size();

        assertEquals(beforeDumpSize + 2, afterDumpSize);
    }

    @Test
    @DisplayName("메모리에 저장된 바우처는 덤프 후 파일에서 찾을 수 있어야 한다.")
    void dumpTest2() {
        Map<UUID, Voucher> cacheMap = new HashMap<>();
        UUID id1 = UUID.randomUUID();

        cacheMap.put(id1, createVoucher(id1, FixedAmount, 5000));
        dumper.dump(cacheMap);


        boolean isSaved = wini.entrySet()
                .stream()
                .map(s -> fromString(s.getValue().getName()))
                .anyMatch(s -> s.equals(id1));

        assertTrue(isSaved);
    }
}