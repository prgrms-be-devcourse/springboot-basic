package org.prgrms.application.view;

import org.prgrms.application.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class OutputView {

    public void printSelect() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }


    public void printStorageList(Map<UUID, Voucher> storage) {
        for (Map.Entry<UUID, Voucher> entry : storage.entrySet()) {
            System.out.println("바우처 정보 : " + entry.getValue().toString());
        }
    }

    public void printSelectVoucherType(){
        System.out.println("Select VoucherType");
        System.out.println("Fixed");
        System.out.println("Percent");
    }
}
