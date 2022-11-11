package org.prgrms.kdt.view;

import org.prgrms.kdt.controller.response.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleView {
    public String introduceCommand() {
        return "=== Voucher Program === \nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    }

    public String requestVoucherInfo() {
        return "======================= \n바우처 타입(fixed, percent)과 할인 정도를 <:>로 구분하여 작성해주세요 \n ex) fixed:10";
    }

    public String exit() {
        return "======================= \n 시스템을 종료합니다.";
    }

    public String wrongCommand() {
        return "======================= \n잘못된 명령입니다.";
    }

    public String saveVoucher() {
        return "======================= \n바우처가 저장되었습니다.";
    }

    public String saveVoucherError() {
        return "======================= \n바우처를 저장할 수 없습니다.";
    }

    public String listVoucher(List<VoucherResponse> list) {
        StringBuilder rtnStr = new StringBuilder("======================= \n");
        for (VoucherResponse v : list) {
            rtnStr.append(v.voucher()).append("\n");
        }
        return rtnStr.toString();
    }

    public String emptyVoucherList() {
        return "======================= \n저장된 바우처가 없습니다.";
    }

}
