package org.prgrms.kdt.view;

import org.prgrms.kdt.controller.response.CustomerResponse;
import org.prgrms.kdt.controller.response.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleView {
    public String introduceCommand() {
        return "=== Voucher Program === " +
                "\nType exit to exit the program." +
                "\nType create-voucher to create a new voucher." +
                "\nType list to list all vouchers." +
                "\nType create-customer to create a new customer" +
                "\nType get-customer to get a customer";
    }

    public String requestCustomerInfoToCreate() {
        return "======================= \n사용자의 이메일을 작성해주세요.";
    }

    public String saveCustomer(){
        return "======================= \n사용자가 저장되었습니다.";
    }

    public String saveCustomerError(){
        return "======================= \n사용자를 저장할 수 없습니다.";
    }

    public String requestCustomerInfoToGet() {
        return "======================= \n찾고자 하는 사용자의 이메일을 입력해주세요.";
    }

    public String getCustomer(CustomerResponse customerResponse){
        return "======================= \n" + customerResponse.customer();
    }

    public String getCustomerError(){
        return "======================= \n존재하지 않는 사용자입니다.";
    }

    public String requestVoucherInfo() {
        return "======================= \n바우처 타입(fixed, percent)과 할인 정도를 <:>로 구분하여 작성해주세요 \n ex) fixed:10";
    }

    public String exit() {
        return "======================= \n시스템을 종료합니다.";
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
