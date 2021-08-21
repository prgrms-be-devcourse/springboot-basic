package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

class VoucherServiceTest {

    private VoucherService voucherService;

    public void init() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
    }

    @Test
    @DisplayName("바우처가 저장된다")
    public void save() throws Exception {
        // given
        init();
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.FIX, 10);

        // when
        voucherService.save(dto);
        // then

        Assertions.assertThat(voucherService.vouchers().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처가 여러개 저장된다")
    public void saveMultipleVoucher() throws Exception {
        // given
        init();
        RequestCreatVoucherDto dto1 = new RequestCreatVoucherDto(VoucherType.FIX, 10);
        RequestCreatVoucherDto dto2 = new RequestCreatVoucherDto(VoucherType.FIX, 11);
        RequestCreatVoucherDto dto3 = new RequestCreatVoucherDto(VoucherType.FIX, 12);
        RequestCreatVoucherDto dto4 = new RequestCreatVoucherDto(VoucherType.FIX, 13);
        RequestCreatVoucherDto dto5 = new RequestCreatVoucherDto(VoucherType.FIX, 14);

        // when
        voucherService.save(dto1);
        voucherService.save(dto2);
        voucherService.save(dto3);
        voucherService.save(dto4);
        voucherService.save(dto5);

        // then

        Assertions.assertThat(voucherService.vouchers().size()).isEqualTo(5);
    }
}