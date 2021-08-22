package org.prgrms.kdt.Model;

import org.prgrms.kdt.TypeStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManageFileVoucher {
    private final String Filepath = System.getProperty("user.dir") + "/voucher.csv";


    public void write(Voucher voucher) throws IOException {
        BufferedWriter bw;
        String aData;


        bw = new BufferedWriter(new FileWriter(Filepath, true));
        String writing = voucher.getVoucherId() + "," + voucher.getVoucherdiscount() + "," + voucher.getType();
        bw.write(writing);
        bw.newLine();

        bw.close();

    }

    public List<Voucher> ReadAll() throws IOException {
        BufferedReader br = null;
        String line = "";
        List<Voucher> vouchers = new ArrayList<>();
        //Voucher voucher;
        br = new BufferedReader(new FileReader(Filepath));
        while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
            String[] array = line.split(",");
            UUID id = UUID.fromString(array[0]);
            long amount = Long.parseLong(array[1]);
            if (TypeStatus.valueOf(array[2]) == TypeStatus.Fixed) {
                vouchers.add(new FixedAmountVoucher(id, amount));
            } else vouchers.add(new PercentDiscountVoucher(id, amount));

        }
        return vouchers;

    }
}
