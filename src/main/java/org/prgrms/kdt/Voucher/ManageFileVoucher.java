package org.prgrms.kdt.Voucher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManageFileVoucher {
    private final String Filepath = System.getProperty("user.dir") + "/voucher.csv";


    public void write(Voucher voucher) {
        BufferedWriter bw;
        String aData;


        try{
            bw = new BufferedWriter(new FileWriter(Filepath, true));
            aData = voucher.getVoucherId() + "," + voucher.getVoucherdiscount() + "," + voucher.getType();
            bw.write(aData);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<Voucher> ReadAll() {
        BufferedReader br = null;
        String line = "";
        List<Voucher> vouchers = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(Filepath));
            while ((line = br.readLine()) != null) {
                String[] array = line.split(",");
                UUID id = UUID.fromString(array[0]);
                long amount = Long.parseLong(array[1]);
                if (TypeStatus.valueOf(array[2]) == TypeStatus.Fixed) {
                    vouchers.add(new FixedAmountVoucher(id, amount));
                } else vouchers.add(new PercentDiscountVoucher(id, amount));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vouchers;

    }
}
