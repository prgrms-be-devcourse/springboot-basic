package org.prgrms.kdt.controller;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.helper.MessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputController {

    private final MessageHelper messageHelper = new MessageHelper();

    public void showVoucherList(List<Voucher> voucherList) {
        if(voucherList.isEmpty()) {
            messageHelper.showVoucherListEmptyMessage();
            return;
        }
        voucherList.forEach(System.out::println);
    }

}