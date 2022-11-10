package org.prgrms.kdt.controller;

import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.CreateVoucherDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public String introduceCommand() {
        return "=== Voucher Program === \nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    }

    public String requestVoucherInfo() {
        return "======================= \n바우처 타입(fixed, percent)과 할인 정도를 <:>로 구분하여 작성해주세요 \n ex) fixed:10";
    }

    public String create(CreateVoucherRequest createVoucherRequest) {
        CreateVoucherDto createVoucherDto = new CreateVoucherDto(createVoucherRequest);
        voucherService.createVoucher(createVoucherDto);
        return "======================= \n바우처가 저장되었습니다.";
    }

    public String list() {
        StringBuilder rtn = new StringBuilder("======================= \n");
        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) return "======================= \n저장된 바우처가 없습니다.";
        for (Voucher voucher : vouchers) {
            rtn.append(voucher).append("\n");
        }
        return rtn.toString();
    }

    public String exit() {
        return "시스템을 종료합니다. \n======================= ";
    }

    public String wrongCommand(){
        return "======================= \n잘못된 명령입니다.";
    }
}
