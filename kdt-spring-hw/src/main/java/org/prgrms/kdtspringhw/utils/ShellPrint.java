package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.UUID;

public class ShellPrint {
    private BufferedWriter bw;

    public ShellPrint() {
        this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void print(String str) throws IOException {
        bw.write(str);
        bw.flush();
    }

    public void printVoucherList(Map<UUID, Voucher> voucherList) throws IOException {
        for (UUID uuid : voucherList.keySet()) {
            Voucher voc = voucherList.get(uuid);
            bw.write(voc.toString() + "\n");
        }
        bw.flush();
    }

    public void printBlackList(Map<UUID, BlackList> blackLists) throws IOException {
        for (UUID uuid : blackLists.keySet()) {
            BlackList blackList = blackLists.get(uuid);
            bw.write(blackList.toString() + "\n");
        }
        bw.flush();
    }

    public void printHome() throws IOException {
        bw.write("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers\n" +
                "Type black to list all black_list\n" +
                "\n"
        );
        bw.flush();
    }
}