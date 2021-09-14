package org.prgrms.kdt.commadLineApp;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OutPutView {


    public static void showBlackList(File blackList) throws IOException {
        List<String> outList = Files.readAllLines(Path.of(blackList.getPath()));
        System.out.println(outList.toString());
    }

    public static void showList(Map<UUID, Voucher> voucherList) {
        for(UUID id : voucherList.keySet())
            System.out.println(voucherList.get(id).toString());
    }

    public static void showCustomerList(List<Customer> customerList) {
        for(Customer customer : customerList){
            System.out.println(customer.toString());
        }
        System.out.println();
    }

    public static void wrongEmail(String email) {
        System.out.println(email + " is wrong input");
    }
}
