package com.programmers.voucher.repository;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class CsvVoucherRepository implements VoucherRepository {
    private static final String SAMPLE_CSV_FILE_PATH = "/Users/tommy/Desktop/dev course/과제/SpringBootBasic/voucher/src/main/resources/sample.csv";
    File file = new File(SAMPLE_CSV_FILE_PATH);
    BufferedWriter bw;
    BufferedReader br;

    @Override
    public Voucher save(Voucher voucher) {
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            String csvDelimiter = isFixedAmountVoucher(voucher) ? fixedAmountVoucherString(voucher) : percentDiscountVoucherString(voucher);
            bw.write(csvDelimiter);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeBufferedWriter();
        }
        return voucher;
    }

    private static boolean isFixedAmountVoucher(Voucher voucher) {
        return voucher instanceof FixedAmountVoucher;
    }

    private String fixedAmountVoucherString(Voucher voucher) {
        return "FixedAmountVoucher," + voucher.getVoucherId() + "," + ((FixedAmountVoucher) voucher).getAmount();
    }

    private String percentDiscountVoucherString(Voucher voucher) {
        return "PercentDiscountVoucher," + voucher.getVoucherId() + "," + ((PercentDiscountVoucher) voucher).getPercent();
    }

    private void closeBufferedWriter() {
        try {
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Voucher> findAll() {
        String line = "";
        HashMap<String, Voucher> voucherHashMap = new LinkedHashMap<>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                List<String> rowInformation = Arrays.asList(line.split(","));
                String type = rowInformation.get(0);
                String id = rowInformation.get(1);
                String information = rowInformation.get(2);
                addFixedAmountVoucher(voucherHashMap, type, id, information);
                addPercentDiscountVoucher(voucherHashMap, type, id, information);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBufferedReader();
        }

        return voucherHashMap;
    }

    private void closeBufferedReader() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFixedAmountVoucher(HashMap<String, Voucher> voucherHashMap, String type, String id, String information) {
        if ("FixedAmountVoucher".equals(type)) {
            voucherHashMap.put(id, new FixedAmountVoucher(id, Long.valueOf(information)));
        }
    }

    private void addPercentDiscountVoucher(HashMap<String, Voucher> voucherHashMap, String type, String id, String information) {
        if ("PercentDiscountVoucher".equals(type)) {
            voucherHashMap.put(id, new PercentDiscountVoucher(id, Long.valueOf(information)));
        }
    }
}
