package org.prgrms.vouchermanager.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.repository.MemoryVoucherRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class VoucherServiceTest {
    @Mock
    private MemoryVoucherRepository repository;

    @InjectMocks
    private VoucherService service;

    @Test
    @DisplayName("관련 repository함수가 정상 호출되어야한다.")
    void createVoucher(){
        //given
        UUID generatedId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(generatedId, 10L);
        //when
        Voucher voucher1 = service.createVoucher(voucher);
        List<Voucher> allVoucher = service.getAllVoucher();
        //then
        verify(repository).save(voucher);
        verify(repository).findAll();
    }
}