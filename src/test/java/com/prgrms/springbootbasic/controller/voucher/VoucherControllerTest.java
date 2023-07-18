package com.prgrms.springbootbasic.controller.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import com.prgrms.springbootbasic.service.voucher.VoucherService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {


    @Mock
    private VoucherService voucherService;

    @InjectMocks
    private VoucherController voucherController;

    @Mock
    private VoucherResponse voucherResponse;

    @Mock
    private VoucherCreateRequest voucherCreateRequest;

    @Mock
    private VoucherListResponse voucherListResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("바우처 생성 요청 테스트")
    void createTest() {
        //given
        when(voucherService.createVoucher(voucherCreateRequest)).thenReturn(voucherResponse);

        //when
        VoucherResponse response = voucherController.create(voucherCreateRequest);

        //then
        assertEquals(voucherResponse, response);
    }

    @Test
    @Order(2)
    @DisplayName("모든 바우처 출력 요청 테스트")
    void findByAllTest() {
        //given
        when(voucherService.findAllVouchers()).thenReturn(voucherListResponse);

        //when
        VoucherListResponse response = voucherController.findAllList();

        //then
        assertEquals(voucherListResponse, response);
    }

    @Test
    @Order(3)
    @DisplayName("특정 id의 바우처 출력 요청 테스트")
    void findByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(voucherService.findById(voucherId)).thenReturn(voucherResponse);

        //when
        VoucherResponse response = voucherController.findById(voucherId);

        //then
        assertEquals(voucherResponse, response);
    }

    @Test
    @Order(3)
    @DisplayName("생성일 순 바우처 출력 요청 테스트")
    void findByCreatedAtTest() {
        //given
        when(voucherService.findByCreateAt()).thenReturn(voucherListResponse);

        //when
        VoucherListResponse response = voucherController.findByCreatedAt();

        //then
        assertEquals(voucherListResponse, response);
    }

    @Test
    @Order(4)
    @DisplayName("바우처 타입별 출력 요청 테스트")
    void findByTypeTest() {
        //given
        VoucherType type = VoucherType.FIXED;
        when(voucherService.findByType(type)).thenReturn(voucherListResponse);

        //when
        VoucherListResponse response = voucherController.findByType(type);

        //then
        assertEquals(voucherListResponse, response);
    }

    @Test
    @Order(4)
    @DisplayName("바우처 수정 요청 테스트")
    void updateTest() {
        //given
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(UUID.randomUUID(), 10L);

        //when
        voucherController.update(voucherUpdateRequest);

        //then
        verify(voucherService).updateVoucher(voucherUpdateRequest);
    }

    @Test
    @Order(5)
    @DisplayName("특정 id의 바우처 삭제 요청 테스트")
    void deleteByIdTest() {
        //given
        UUID voucherId = UUID.randomUUID();
        when(voucherService.deleteById(voucherId)).thenReturn(1);

        //when
        int result = voucherController.deleteById(voucherId);

        //then
        assertEquals(1, result);
    }

    @Test
    @Order(6)
    @DisplayName("모든 바우처 삭제 요청 테스트")
    void deleteAllTest() {
        //given

        //when
        voucherController.deleteAll();

        //then
        verify(voucherService).deleteAllVoucher();
    }
}


