package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile(value = "dev")
public class CsvVoucherStream implements VoucherStream {
    @Value("${filepath.voucher}")
    private String sampleCsvFilePath;
    File file;
    BufferedWriter bw;
    BufferedReader br;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public Voucher save(Voucher voucher) {
        try {
            String path = resourceLoader.getResource(sampleCsvFilePath).getURI().getPath();
            file = new File(path);
            bw = new BufferedWriter(new FileWriter(file, true));
            String csvDelimiter = isFixedAmountVoucher(voucher) ? fixedAmountVoucherString(voucher) : percentDiscountVoucherString(voucher);
            bw.write(csvDelimiter);
            bw.newLine();
        } catch (IOException e) {
            log.warn("BufferedWriter 동작 중 에러 발생 | [error] : {}", e.getMessage());
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
            log.warn("BufferedWriter 종료 중 에러 발생 | [error] : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Voucher> findAll() {
        HashMap<String, Voucher> voucherHashMap = new LinkedHashMap<>();
        loadCSVFile(voucherHashMap);
        return voucherHashMap;
    }

    private void loadCSVFile(HashMap<String, Voucher> voucherHashMap) {
        String line = "";
        try {
            String path = resourceLoader.getResource(sampleCsvFilePath).getURI().getPath();
            file = new File(path);
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                putDataToHashMap(voucherHashMap, line);
            }
        } catch (Exception e) {
            log.warn("CSV파일 읽어들이는 도중 에러 발생 | [error] : {}", e.getMessage());
            e.printStackTrace();
        } finally {
            closeBufferedReader();
        }
    }

    private static void putDataToHashMap(HashMap<String, Voucher> voucherHashMap, String line) {
        List<String> rowInformation = Arrays.asList(line.split(","));
        String type = rowInformation.get(0);
        String id = rowInformation.get(1);
        String information = rowInformation.get(2);
        voucherHashMap.put(id, ("FixedAmountVoucher".equals(type) ? 
                new FixedAmountVoucher(id, Long.valueOf(information)) : new PercentDiscountVoucher(id, Long.valueOf(information))));
    }

    private void closeBufferedReader() {
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            log.warn("BufferedReader 종료 중 에러 발생 | [error] : {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
