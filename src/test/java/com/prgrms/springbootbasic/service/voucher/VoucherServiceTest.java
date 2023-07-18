package com.prgrms.springbootbasic.service.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    private VoucherResponse createdVoucher;

    @BeforeEach
    void init() {
        VoucherCreateRequest request = new VoucherCreateRequest(10000, VoucherType.FIXED, LocalDateTime.now());
        createdVoucher = voucherService.createVoucher(request);
    }


    //해피 케이스 테스트
    @Test
    @DisplayName("바우처 생성 테스트")
    void createVoucherTest() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(10000, VoucherType.FIXED, LocalDateTime.now());

        //when
        VoucherResponse response = voucherService.createVoucher(request);

        //then
        assertNotNull(response);
        assertNotNull(response.getVoucherId());
        assertEquals(request.getDiscount(), response.getDiscount());
        assertEquals(request.getType(), response.getType());
        assertNotNull(response.getCreateAt());
    }

    @Test
    @DisplayName("저장된 모든 바우처 조회")
    void findByAllTest() {
        //given

        //when
        VoucherListResponse response = voucherService.findAllVouchers();

        //then
        assertNotNull(response);
        assertFalse(response.getVoucherResponseList().isEmpty());
    }

    @Test
    @DisplayName("바우처 조회 - 생성일 순으로 조회")
    void findByCreatedAtTest() {
        //given

        //when
        VoucherListResponse response = voucherService.findByCreateAt();

        //then
        assertNotNull(response);
        assertFalse(response.getVoucherResponseList().isEmpty());
    }

    @Test
    @DisplayName("바우처 타입별로 조회")
    void findByTypeTest() {
        //given
        VoucherType type = VoucherType.FIXED;

        //when
        VoucherListResponse response = voucherService.findByType(type);

        //then
        assertNotNull(response);
        assertFalse(response.getVoucherResponseList().isEmpty());
    }

    @Test
    @DisplayName("바우처 조회 - id를 통해서 조회")
    void findByIdTest() {
        //given
        UUID voucherId = createdVoucher.getVoucherId();

        //when
        VoucherResponse response = voucherService.findById(voucherId);

        //then
        assertNotNull(response);
        assertEquals(voucherId, response.getVoucherId());
    }

    @Test
    @DisplayName("바우처 수정 테스트")
    void updateVoucherTest() {
        //given
        UUID voucherId = createdVoucher.getVoucherId();
        long newDiscount = 20000;
        VoucherUpdateRequest request = new VoucherUpdateRequest(voucherId, newDiscount);

        //when
        voucherService.updateVoucher(request);
        VoucherResponse updatedVoucher = voucherService.findById(voucherId);

        //then
        assertEquals(newDiscount, updatedVoucher.getDiscount());
    }

    @Test
    @DisplayName("바우처 삭제 - id를 통해서 삭제")
    void deleteByIdTest() {
        //given
        UUID voucherId = createdVoucher.getVoucherId();

        //when
        int result = voucherService.deleteById(voucherId);

        //then
        assertEquals(1, result);
        assertThrows(IllegalArgumentException.class, () -> voucherService.findById(voucherId));
    }

    @Test
    @DisplayName("바우처 삭제 - 저장된 모든 바우처 삭제")
    void deleteByAllTest() {
        //given

        //when
        voucherService.deleteAllVoucher();

        //then
        VoucherListResponse response = voucherService.findAllVouchers();
        assertTrue(response.getVoucherResponseList().isEmpty());
    }


    //엣지 케이스
    @Test
    @DisplayName("바우처 생성 실패  테스트 케이스")
    void createFailTest() {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(-10000, VoucherType.FIXED, LocalDateTime.now());

        //then
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(request));
    }

    @Test
    @DisplayName("모든 바우처 조회 실패 테스트 케이스")
    void findAllFailTest() {
        //given
        voucherService.deleteAllVoucher();

        //when
        VoucherListResponse response = voucherService.findAllVouchers();

        //then
        assertTrue(response.getVoucherResponseList().isEmpty());
    }

    @Test
    @DisplayName("생성일 순 조회 실패 테스트 케이스")
    void findByCreatedAtFailTest() {
        //given
        voucherService.deleteAllVoucher();

        //when
        VoucherListResponse response = voucherService.findByCreateAt();

        //then
        assertTrue(response.getVoucherResponseList().isEmpty());
    }
    

    @Test
    @DisplayName("id 조회 실패 테스트 케이스")
    void findByIdFailTest() {
        //given
        UUID nonExistentVoucherId = UUID.randomUUID();

        //then
        assertThrows(IllegalArgumentException.class, () -> voucherService.findById(nonExistentVoucherId));
    }

    @Test
    @DisplayName("수정 실패 테스트 케이스")
    void updateFailTest() {
        //given
        UUID nonExistentVoucherId = UUID.randomUUID();
        VoucherUpdateRequest request = new VoucherUpdateRequest(nonExistentVoucherId, 20000);

        //when - then
        assertThrows(IllegalArgumentException.class, () -> voucherService.updateVoucher(request));
    }

    @Test
    @DisplayName("id를 이용한 삭제 실패 테스트 케이스")
    void deleteByIdFailTest() {
        //given
        UUID nonExistentVoucherId = UUID.randomUUID();

        //when - then
        assertThrows(NoSuchElementException.class, () -> voucherService.deleteById(nonExistentVoucherId));
    }

    @Test
    @DisplayName("저장된 VoucherId 체크 실패 테스트 케이스")
    void existByIdFailTest() {
        //given
        UUID nonExistentVoucherId = UUID.randomUUID();

        //when
        boolean result = voucherService.existById(nonExistentVoucherId);

        //then
        assertFalse(result);
    }
}

