package com.prgrms.springbootbasic.repository.voucher;


import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import com.prgrms.springbootbasic.domain.voucher.FixedVoucher;
import com.prgrms.springbootbasic.domain.voucher.RateVoucher;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class VoucherJdbcRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;


    //해피 케이스 테스트
    @Test
    @DisplayName("데이터 베이스와 연동되어 바우처 생성 테스트")
    void createVoucherTest() {
        //given
        VoucherCreateRequest createFixedVoucherRequest = new VoucherCreateRequest(5000, VoucherType.FIXED, LocalDateTime.now());
        VoucherCreateRequest createRateVoucherRequest = new VoucherCreateRequest(50, VoucherType.RATE, LocalDateTime.now());

        //when
        Voucher fixedVoucher = new FixedVoucher(10000);
        Voucher rateVoucher = new RateVoucher(50);

        //then
        assertNotNull(fixedVoucher);
        assertNotNull(rateVoucher);

        //FixedVoucher
        assertEquals(fixedVoucher.getVoucherId(), createdFixedVoucher.getVoucherId());
        assertEquals(fixedVoucher.getDiscount(), createdFixedVoucher.getDiscount());
        assertEquals(fixedVoucher.getVoucherType(), createdFixedVoucher.getVoucherType());
        assertEquals(fixedVoucher.getCreatedAt(), createdFixedVoucher.getCreatedAt());

        //RateVoucher
        assertEquals(rateVoucher.getVoucherId(), createdRateVoucher.getVoucherId());
        assertEquals(rateVoucher.getVoucherId(), createdFixedVoucher.getVoucherId());
        assertEquals(rateVoucher.getDiscount(), createdFixedVoucher.getDiscount());
        assertEquals(rateVoucher.getVoucherType(), createdFixedVoucher.getVoucherType());
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 모든 바우처 조회")
    void findByAllTest() {
        //given
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(rateVoucher);

        //when
        List<Voucher> allVouchers = voucherRepository.findAll();

        //then
        assertEquals(2, allVouchers.size());
        assertTrue(allVouchers.stream().anyMatch(v -> v.getVoucherId().equals(fixedVoucher.getVoucherId())));
        assertTrue(allVouchers.stream().anyMatch(v -> v.getVoucherId().equals(rateVoucher.getVoucherId())));
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 생성일 순으로 바우처 조회")
    void findByCreatedAtTest() {
        //given
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(rateVoucher);

        //when
        List<Voucher> vouchersByCreatedAt = voucherRepository.findByCreatedAt();

        //then
        assertEquals(2, vouchersByCreatedAt.size());
        assertTrue(vouchersByCreatedAt.stream().anyMatch(v -> v.getVoucherId().equals(fixedVoucher.getVoucherId())));
        assertTrue(vouchersByCreatedAt.stream().anyMatch(v -> v.getVoucherId().equals(rateVoucher.getVoucherId())));
    }

    @Test
    @DisplayName("데이터 베이스에 저장된 바우처를 타입별로 조회")
    void findByTypeTest() {
        //given
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(rateVoucher);

        //when
        List<Voucher> fixedVouchers = voucherRepository.findByType(VoucherType.FIXED);
        List<Voucher> rateVouchers = voucherRepository.findByType(VoucherType.RATE);

        //then

        assertEquals(fixedVoucher.getVoucherId(), fixedVouchers.get(0).getVoucherId());
        assertEquals(rateVoucher.getVoucherId(), rateVouchers.get(0).getVoucherId());

    }

    @Test
    @DisplayName("데이터 베이스에 저장된 바우처 id를 통해서 조회")
    void findByIdTest() {
        //given
        Voucher savedFixedVoucher = voucherRepository.save(fixedVoucher);

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(savedFixedVoucher.getVoucherId());

        //then
        assertTrue(foundVoucher.isPresent());
        assertEquals(savedFixedVoucher.getVoucherId(), foundVoucher.get().getVoucherId());

    }

    @Test
    @DisplayName("데이터 베이스에 저장된 바우처 수정 테스트")
    void updateVoucherTest() {
        //given
        Voucher savedFixedVoucher = voucherRepository.save(fixedVoucher);
        long newDiscount = 2000;

        //when
        //VoucherUpdateRequest updateRequest = new VoucherUpdateRequest(savedFixedVoucher.getVoucherId(), newDiscount);

        voucherRepository.update(savedFixedVoucher);

        //then
        assertEquals(newDiscount, savedFixedVoucher.getDiscount());

    }

    @Test
    @DisplayName("데이터 베이스에 저장된 바우처의 id를 통해서 삭제")
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        FixedVoucher fixedVoucher = new FixedVoucher(voucherId, 5000, LocalDateTime.now());

        //when
        voucherRepository.save(fixedVoucher);
        voucherRepository.deleteById(voucherId);

        //then
        assertThat(voucherRepository.findById(voucherId).isEmpty());

    }


    @Test
    @DisplayName("데이터 베이스에 저장된 모든 바우처 삭제")
    void deleteByAllTest() {
        //given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();
        UUID voucherId3 = UUID.randomUUID();
        UUID voucherId4 = UUID.randomUUID();

        FixedVoucher fixedVoucher1 = new FixedVoucher(voucherId1, 5000, LocalDateTime.now());
        FixedVoucher fixedVoucher2 = new FixedVoucher(voucherId2, 10000, LocalDateTime.now());

        RateVoucher rateVoucher1 = new RateVoucher(voucherId3, 50, LocalDateTime.now());
        RateVoucher rateVoucher2 = new RateVoucher(voucherId4, 70, LocalDateTime.now());

        //when
        voucherRepository.save(fixedVoucher1);
        voucherRepository.save(fixedVoucher2);
        voucherRepository.save(rateVoucher1);
        voucherRepository.save(rateVoucher2);
        voucherRepository.deleteAll();

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(0);
    }
}