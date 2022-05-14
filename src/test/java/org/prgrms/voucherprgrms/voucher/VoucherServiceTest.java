package org.prgrms.voucherprgrms.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.prgrms.voucherprgrms.voucher.model.VoucherSearchParam;
import org.prgrms.voucherprgrms.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;


@SpringJUnitConfig
class VoucherServiceTest {

    @Configuration
    @ComponentScan("org.prgrms.voucherprgrms")
    static class Config {
    }


    VoucherCreator voucherCreator = mock(VoucherCreator.class);
    VoucherRepository voucherRepository = mock(VoucherRepository.class);
    VoucherService voucherService = new VoucherService(voucherRepository, voucherCreator);

    @BeforeEach
    void init() {
    }

    @Test
    @DisplayName("voucher 생성")
    void createVoucherTest() {

        //given
        var voucherForm = new VoucherForm("FixedAmountVoucher", 1000);
        var expectedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        when(voucherCreator.create(voucherForm)).thenReturn(expectedVoucher);

        //when
        var voucher = voucherService.createVoucher(voucherForm);

        verify(voucherRepository).insert(expectedVoucher);

    }

    @Test
    @DisplayName("Voucher 검색 테스트")
    void searchVoucherTest() {

        //given
        var searchParam = new VoucherSearchParam("CreatedAt", "", "2022-05-09", "2022-05-10");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDate.parse(searchParam.getStartDate(), formatter).atStartOfDay();
        LocalDateTime end = LocalDate.parse(searchParam.getEndDate(), formatter).atStartOfDay();
        List<Voucher> expectedList = List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000));

        //when
        when(voucherRepository.findByCreated(start,end))
                .thenReturn(expectedList);

        //then
        var voucherList = voucherService.search(searchParam);
        assertThat(voucherList, hasSize(1));
        assertThat(voucherList, samePropertyValuesAs(expectedList));
    }

}