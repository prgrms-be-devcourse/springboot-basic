package com.programmers.kdtspringorder.command;

import com.programmers.kdtspringorder.customer.Customer;
import com.programmers.kdtspringorder.customer.CustomerService;
import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.VoucherService;
import com.programmers.kdtspringorder.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;


public record VoucherListCommandAction(Input input,
                                       Output output,
                                       VoucherService voucherService,
                                       CustomerService customerService) implements CommandAction {


    @Override
    public void act() {
        List<Voucher> voucherList = voucherService.findAll();
        showVouchers(voucherList);
        output.printMessage("바우처의 번호를 입력하면 소유한 고객의 정보를 확인할 수 있습니다");
        int index = Integer.parseInt(input.inputText()) - 1;
        UUID customerId = voucherList.get(index).getCustomerId();
        if (customerId == null) {
            output.printMessage("보유한 고객이 없습니다.");
            return;
        }
        Customer customer = customerService.findById(customerId).get();
        output.printMessage(customer.toString());
    }

    private void showVouchers(List<Voucher> voucherList) {
        for (int i = 0; i < voucherList.size(); i++) {
            System.out.println(MessageFormat.format("{0}. {1}", (i + 1), voucherList.get(i).toString()));
        }
    }
}
