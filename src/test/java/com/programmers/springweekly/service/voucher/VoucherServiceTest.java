package com.programmers.springweekly.service.voucher;

import static java.util.Map.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import com.programmers.springweekly.service.VoucherService;
import java.util.Map;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    @Test
    @DisplayName("바우처를 모두 조회한다.")
    void getVoucherList() {
        // given
        Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");
        Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.PERCENT, "10");

        Map<UUID, Voucher> voucherMap = Map.of(
                voucher1.getVoucherId(), voucher1,
                voucher2.getVoucherId(), voucher2
        );

        // when
        given(voucherRepository.getList()).willReturn(voucherMap);
        Map<UUID, Voucher> voucherAll = voucherService.findVoucherAll();

        // then
        Assertions.assertThat(voucherAll)
                .hasSize(2)
                .contains(entry(voucher1.getVoucherId(), voucher1), entry(voucher2.getVoucherId(), voucher2));

        verify(voucherRepository, times(1)).getList();
    }

    /*
    @Test
    @DisplayName("바우처를 저장한다.")
    void saveVoucher() {
        // given
        Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");

        // when
        voucherService.saveVoucher(VoucherType.FIXED, "1000");

        // then
        verify(voucherRepository).save(voucher);
    }
    */
}
