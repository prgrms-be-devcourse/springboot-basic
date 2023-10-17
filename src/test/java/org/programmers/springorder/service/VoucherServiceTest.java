package org.programmers.springorder.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.dto.VoucherResponseDto;
import org.programmers.springorder.model.Voucher;
import org.programmers.springorder.model.VoucherType;
import org.programmers.springorder.repository.MemoryVoucherRepository;
import org.programmers.springorder.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {

    private static final VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private static final VoucherService voucherService = new VoucherService(voucherRepository);

    private static final Logger log = LoggerFactory.getLogger(VoucherServiceTest.class);

    private final List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    @BeforeEach
    void init(){
        voucherRepository.save(new Voucher(uuids.get(0),10, VoucherType.PERCENT));
        voucherRepository.save(new Voucher(uuids.get(1),5, VoucherType.PERCENT));
        voucherRepository.save(new Voucher(uuids.get(2),1000, VoucherType.FIXED));
        voucherRepository.save(new Voucher(uuids.get(3), 2000, VoucherType.FIXED));
    }

    @AfterEach
    void clear(){
        voucherRepository.clear();
    }

    @Test
    @DisplayName("모든 Voucher 리스트를 가져오는 Service 로직")
    void getAllVoucher() {
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();
        allVoucher.forEach(voucher -> log.info("voucher.toString = {}",voucher));

        assertThat(allVoucher).hasSize(4);
        assertThat(allVoucher.stream()
                .map(VoucherResponseDto::getVoucherId)
                .allMatch(uuids::contains))
                .isTrue();

    }

    @Test
    @DisplayName("Voucher를 저장하는 Service 로직")
    void saveNewVoucher() {
        //given
        VoucherRequestDto requestDto = new VoucherRequestDto(100, VoucherType.FIXED);
        List<VoucherResponseDto> beforeSaveVoucher = voucherService.getAllVoucher();
        assertThat(beforeSaveVoucher).hasSize(4);

        //when
        voucherService.save(requestDto);
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();

        //then
        assertThat(allVoucher).hasSize(5);
    }
}