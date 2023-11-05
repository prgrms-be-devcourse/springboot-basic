package com.prgms.vouchermanager.util.file;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.prgms.vouchermanager.domain.voucher.VoucherType.valueOf;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_READ_FILE;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_WRITE_FILE;

/**
 * 1. 바우처 FIle을 Repo에 return하기
 * 2. Repo를 바우처 File에 저장하기
 * <p>
 * 3. BlackList File을 Repo에 return하기
 */
@Component
public class FileManager {

    private final String voucherListPath;
    private final String blackListPath;


    public FileManager(
            @Value("${file.path.voucher}") String voucherListPath,
            @Value("${file.path.blacklist}") String blackListPath) {
        this.voucherListPath = voucherListPath;
        this.blackListPath = blackListPath;

    }

    public Map<UUID, Voucher> readVoucherCsv() {
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(voucherListPath)))) {
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(", ");

                UUID id = UUID.fromString(split[0]);
                Long value = Long.parseLong(split[1]);
                VoucherType voucherType = valueOf(split[2]);
                LocalDateTime createdAt = LocalDateTime.now();
                if (voucherType == VoucherType.FIXED_AMOUNT) {
                    voucherMap.put(id, new FixedAmountVoucher(id, value, createdAt));
                } else if (voucherType == VoucherType.PERCENT_DISCOUNT) {
                    voucherMap.put(id, new PercentDiscountVoucher(id, value, createdAt));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(INVALID_READ_FILE.getMessage());
        }
        return voucherMap;
    }


    public Map<Long, Customer> readBlackListCsv() {

        Map<Long, Customer> customerMap = new ConcurrentHashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(blackListPath)))) {
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(", ");

                Long id = Long.parseLong(split[0]);
                String name = split[1];
                String email = split[2];
                boolean blackList = split[3].equals("1");

                customerMap.put(id, new Customer(id, name, email, blackList));
            }
        } catch (IOException e) {
            throw new RuntimeException(INVALID_READ_FILE.getMessage());
        }
        return customerMap;
    }

    public void saveVoucherFile(Map<UUID, Voucher> voucherMap) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(voucherListPath))) {
            bw.write("id, discount value, voucher type");
            bw.newLine();
            for (Voucher voucher : new ArrayList<>(voucherMap.values())) {
                bw.write(voucher.getId() + ", " + voucher.getDiscountValue() + ", " + voucher.getVoucherType());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(INVALID_WRITE_FILE.getMessage());
        }
    }

}

