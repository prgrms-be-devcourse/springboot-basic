package com.prgrms.springbootbasic.controller.voucher;

import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.service.voucher.VoucherService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    //(Create)바우처 생성
    public void create(VoucherCreateRequest voucherCreateRequest) {
        voucherService.createVoucher(voucherCreateRequest);
    }


    //(Read) 바우처의 모든 리스트 출력
    public VoucherListResponse findAllList() {
        return voucherService.findAllVouchers();
    }

    //(Read) 특정 바우처 id의 내용을 출력
    public VoucherResponse findById(UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    //(Read) 바우처 생성기간 출력
    public VoucherResponse findByCreateAt(LocalDateTime createAt) {
        return voucherService.findByCreateAt(createAt);
    }

    //(Read) 바우처 타입별 리스트 출력
    public VoucherListResponse findByType(VoucherType type) {
        return voucherService.findByType(type);
    }

    //(Update) 생성된 바우처 수정
    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        voucherService.updateVoucher(voucherUpdateRequest);
    }

    //(Delete) 특정 바우처를 찾아 삭제
    public void deleteById(UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

    //(Delete) 모든 바우처의 내용 삭제
    public void deleteAll() {
        voucherService.deleteAllVoucher();
    }
}
