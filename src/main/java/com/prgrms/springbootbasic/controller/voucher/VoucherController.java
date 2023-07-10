package com.prgrms.springbootbasic.controller.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.service.voucher.VoucherService;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    //(Create)바우처 생성
    public void create(VoucherType type, long discount) {
        voucherService.createVoucher(type, discount);
    }

    //(Read) 바우처의 모든 리스트 출력
    public Map<UUID, Voucher> findAllList() {
        return voucherService.fetchAllVouchers();
    }

    //(Read) 특정 바우처 id의 내용을 출력
    public void findByid() {

    }

    //(Read) 바우처 타입  내용을 출력
    public void findByType() {

    }

    //(Read) 바우처 생성기간 출력
    public void findByCreateAt() {

    }

    //(Update) 생성된 바우처 수정
    public void update() {

    }

    //(Delete) 특정 바우처를 찾아 삭제
    public void deleteByid() {

    }

    //(Delete) 모든 바우처의 내용 삭제
    public void deleteAll() {

    }
}
