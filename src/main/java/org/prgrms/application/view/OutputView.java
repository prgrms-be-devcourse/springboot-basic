package org.prgrms.application.view;

import org.prgrms.application.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class OutputView {

    public void printSelection(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }


    public void printStorageList(Map<UUID, Voucher> storage) {
        System.out.println(storage);
    }
}
