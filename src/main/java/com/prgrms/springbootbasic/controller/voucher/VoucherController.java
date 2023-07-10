package com.prgrms.springbootbasic.controller.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.service.voucher.VoucherService;
import java.time.LocalDateTime;
import java.util.Map;
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
    public Map<UUID, Voucher> findAllList() {
        return voucherService.fetchAllVouchers();
    }

    //(Read) 특정 바우처 id의 내용을 출력
    public VoucherResponse findByid(UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    //(Read) 바우처 타입  내용을 출력
    public VoucherResponse findByType(VoucherType voucherType) {
        return voucherService.finByType(VoucherType);
    }

    //(Read) 바우처 생성기간 출력
    public VoucherResponse findByCreateAt(LocalDateTime createAt) {
        return voucherService.findByCreateAt(createAt);
    }

    //(Update) 생성된 바우처 수정
    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        voucherService.update(voucherUpdateRequest);
    }

    //(Delete) 특정 바우처를 찾아 삭제
    public void deleteByid() {
        voucherService.deleteById();
    }

    //(Delete) 모든 바우처의 내용 삭제
    public void deleteAll() {
        voucherService.deleteAllVoucher();
    }
}
