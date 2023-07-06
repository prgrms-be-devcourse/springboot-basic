package com.programmers.springweekly.service.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import com.programmers.springweekly.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

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
        Voucher voucher1 = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.FIXED, "1000");
        Voucher voucher2 = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.PERCENT, "10");

        List<Voucher> voucherList = List.of(voucher1, voucher2);

        given(voucherRepository.findAll()).willReturn(voucherList);

        // when
        List<Voucher> voucherAll = voucherService.findVoucherAll();

        // then
        assertThat(voucherAll).usingRecursiveFieldByFieldElementComparator().isEqualTo(voucherList);

        then(voucherRepository).should(times(1)).findAll();
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
