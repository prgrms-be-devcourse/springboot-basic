package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVInOut {

    private final String path;
    private final boolean canAppand;

    public CSVInOut(String path, boolean canAppand) {
        this.path = path;
        this.canAppand = canAppand;
    }
    /*
    public static void main(String[] args) {
        CSVIO csvReader = new CSVIO();
        Voucher fixed = new FixedAmountVoucher(UUID.randomUUID(), 20);
        Voucher percentage = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(fixed);
        vouchers.add(percentage);

        csvReader.writeCSV(vouchers);

        List<String> result = csvReader.readCSV();
        for (String s : result) {
            System.out.println(s);
        }
    }
     */

    public List<String> readCSV() {
        List<String> csvList = new ArrayList<>();
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                csvList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvList;
    }

    public void writeCSV(Voucher voucher) {
        File csv = new File(path);
        BufferedWriter bw = null; // 출력 스트림 생성
        try {
            csv.createNewFile();
            bw = new BufferedWriter(new FileWriter(csv, canAppand));

            String data = voucher.toString();
            bw.write(data);
            bw.newLine(); // 개행
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
