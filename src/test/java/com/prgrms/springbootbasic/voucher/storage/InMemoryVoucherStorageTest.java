package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryVoucherStorageTest {

    private InMemoryVoucherStorage inMemoryVoucherStorage;

    @Mock
    private Voucher voucher;
    @Mock
    List<Voucher> voucherList;

    @BeforeEach
    public void init() {
        this.inMemoryVoucherStorage = new InMemoryVoucherStorage();
        this.voucher = mock(Voucher.class);
        this.voucherList = Arrays.asList(
                mock(Voucher.class),
                mock(Voucher.class),
                mock(Voucher.class));
    }

    @Test
    @DisplayName("Voucher를 Map에 저장할 수 있다.")
    public void save() {
        //given
        when(voucher.getUUID()).thenReturn(UUID.randomUUID());
        when(voucher.getDiscountRate()).thenReturn(10);
        UUID uuid = voucher.getUUID();

        //when
        inMemoryVoucherStorage.save(voucher);
        Optional<Voucher> found = inMemoryVoucherStorage.findById(uuid);

        //then

        assertThat(found.isPresent()).isTrue();
        Voucher foundVoucher = found.get();

        assertThat(foundVoucher.getDiscountRate()).isEqualTo(10);
    }

    @Test
    @DisplayName("Map에 저장한 모든 Voucher들을 조회할 수 있다.")
    public void list() {
        //given
        for (Voucher value : voucherList) {
            when(value.getUUID()).thenReturn(UUID.randomUUID());
        }

        //when
        voucherList.forEach(voucher -> inMemoryVoucherStorage.save(voucher));
        List<Voucher> afterSave = inMemoryVoucherStorage.findAll();

        //then
        assertThat(afterSave.size()).isEqualTo(voucherList.size());
    }
}