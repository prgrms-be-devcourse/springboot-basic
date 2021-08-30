package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
@ComponentScan("org.prgrms.kdt.voucher")
class VoucherServiceTest {

    @Test
    @DisplayName("voucher가 생성되어야한다.")
    void testInsert(){

        VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepositoryMock);
        Voucher voucher = voucherService.createVoucher(VoucherType.FIXED, "1000");

        assertThat(voucher.getVoucherAmount(), is(1000L));
        assertThat(voucher.getVoucherType(), is(VoucherType.FIXED));
    }
}