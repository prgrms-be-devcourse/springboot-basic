package com.wonu606.vouchermanager.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VouchersTest {

    @Test
    @DisplayName("저장된 객체를 Iterator로 꺼낼 수 있어야 한다.")
    void testIterator() {
        // given
        Voucher voucher1 = new PercentageVoucher(UUID.randomUUID(), 50.0d);
        Voucher voucher2 = new PercentageVoucher(UUID.randomUUID(), 50.0d);

        List<Voucher> voucherList = new ArrayList<>(Arrays.asList(voucher1, voucher2));
        Vouchers vouchers = new Vouchers(voucherList);

        // when
        Iterator<Voucher> iterator = vouchers.iterator();

        // then
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("저장된 객체의 개수를 반환해야 한다.")
    void testSize() {
        // given
        Voucher voucher1 = new PercentageVoucher(UUID.randomUUID(), 50.0d);
        Voucher voucher2 = new PercentageVoucher(UUID.randomUUID(), 50.0d);

        List<Voucher> voucherList = new ArrayList<>(Arrays.asList(voucher1, voucher2));
        Vouchers vouchers = new Vouchers(voucherList);

        // when
        int size = vouchers.size();

        // then
        assertEquals(2, size);
    }
}