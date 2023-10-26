package org.prgrms.vouchermanager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.MenuType;
import org.prgrms.vouchermanager.exception.NotExistVoucherException;
import org.prgrms.vouchermanager.repository.voucher.MemoryVoucherRepository;
import org.prgrms.vouchermanager.testdata.VoucherData;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VoucherServiceTest {
    @Mock
    private MemoryVoucherRepository repository;

    @InjectMocks
    private VoucherService service;

    @Test
    @DisplayName("바우처를 등록할 수 있다")
    void create() {
        Voucher fixedVoucher = VoucherData.getFixedVoucher();
        when(repository.save(any(Voucher.class))).thenReturn(fixedVoucher);

        Voucher voucher = service.createVoucher(fixedVoucher);

        assertThat(voucher).isEqualTo(fixedVoucher);
        verify(repository).save(any(Voucher.class));
    }

    @Nested
    @DisplayName("바우처 조회")
    class find {
        @Test
        @DisplayName("id를 통해 바우처를 조회할 수 있다")
        void findById() {
            Voucher fixedVoucher = VoucherData.getFixedVoucher();
            when(repository.findByID(any(UUID.class))).thenReturn(Optional.of(fixedVoucher));

            Optional<Voucher> voucher = service.findById(fixedVoucher.getVoucherId());

            assertThat(voucher.get()).isEqualTo(fixedVoucher);
            verify(repository).findByID(any());
        }

        @Test
        @DisplayName("없는 id로 조회 시 바우처 존재 예외가 터진다.")
        void findByIdNotExists() {
            Voucher fixedVoucher = VoucherData.getFixedVoucher();

            Assertions.assertThrows(NotExistVoucherException.class, () ->
                    service.findById(fixedVoucher.getVoucherId()));
        }

        @Test
        @DisplayName("모든 바우처를 조회할 수 있다")
        void findAll(){
            List<Voucher> result = new ArrayList<>();
            Voucher fixedVoucher = VoucherData.getFixedVoucher();
            result.add(fixedVoucher);

            when(repository.findAll()).thenReturn(result);

            List<Voucher> allVoucher = service.getAllVoucher();

            assertThat(result).isEqualTo(allVoucher);
            verify(repository).findAll();
        }
    }

}