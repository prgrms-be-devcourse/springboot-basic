package com.programmers.voucher.stream.voucher;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile(value = "csv")
public class CsvVoucherStream implements VoucherStream {
    @Value("${filepath.voucher}")
    private String sampleCsvFilePath;
    @Value("${name.voucher.fixed-amount-voucher}")
    private String fixedAmountVoucher;
    @Value("${name.voucher.percent-discount-voucher")
    private String percentDiscountVoucher;
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public Voucher save(Voucher voucher) {
        BufferedWriter bufferedWriter = null;
        File file;
        try {
            String path = resourceLoader.getResource(sampleCsvFilePath).getURI().getPath();
            file = new File(path);
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            String csvDelimiter = isFixedAmountVoucher(voucher) ? fixedAmountVoucherString(voucher) : percentDiscountVoucherString(voucher);
            bufferedWriter.write(csvDelimiter);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            closeBufferedWriter(bufferedWriter);
        }
        return voucher;
    }

    private static boolean isFixedAmountVoucher(Voucher voucher) {
        return voucher instanceof FixedAmountVoucher;
    }

    private String fixedAmountVoucherString(Voucher voucher) {
        return this.fixedAmountVoucher + ", " + voucher.getVoucherId() + "," + ((FixedAmountVoucher) voucher).getAmount();
    }

    private String percentDiscountVoucherString(Voucher voucher) {
        return this.percentDiscountVoucher + ", " + voucher.getVoucherId() + "," + ((PercentDiscountVoucher) voucher).getPercent();
    }

    private void closeBufferedWriter(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Voucher> findAll() {
        HashMap<String, Voucher> voucherHashMap = new LinkedHashMap<>();
        loadCSVFile(voucherHashMap);
        return voucherHashMap;
    }

    private void loadCSVFile(HashMap<String, Voucher> voucherHashMap) {
        File file;
        BufferedReader bufferedReader = null;
        String line = "";
        try {
            String path = resourceLoader.getResource(sampleCsvFilePath).getURI().getPath();
            file = new File(path);
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((line = bufferedReader.readLine()) != null) {
                putDataToHashMap(voucherHashMap, line);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }

    private void putDataToHashMap(HashMap<String, Voucher> voucherHashMap, String line) {
        List<String> rowInformation = Arrays.asList(line.split(","));
        String type = rowInformation.get(0);
        String id = rowInformation.get(1);
        String information = rowInformation.get(2);
        voucherHashMap.put(id, (this.fixedAmountVoucher.equals(type) ?
                new FixedAmountVoucher(id, Long.valueOf(information)) : new PercentDiscountVoucher(id, Long.valueOf(information))));
    }

    private void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
