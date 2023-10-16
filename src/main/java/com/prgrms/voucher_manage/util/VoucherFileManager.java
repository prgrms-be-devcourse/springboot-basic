package com.prgrms.voucher_manage.util;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VoucherFileManager {
    private final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    public Map<UUID, Voucher> loadVoucherData(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                switch(data[2]){
                    case "FIXED" -> {
                        FixedAmountVoucher voucher = new FixedAmountVoucher(Long.valueOf(data[1]));
                        voucherStorage.put(UUID.fromString(data[0]), voucher);
                    }
                    case "PERCENT" -> {
                        PercentAmountVoucher voucher = new PercentAmountVoucher(Long.valueOf(data[1]));
                        voucherStorage.put(UUID.fromString(data[0]), voucher);
                    }
                }
            }
        }  catch (Exception e){
            System.out.println(e.getMessage());
        }
        return voucherStorage;
    }

    public void updateFile(Map<UUID, Voucher> storage, String path){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for( UUID key : storage.keySet() ){
                Voucher voucher = storage.get(key);
                bw.write(voucher.getVoucherId() + "," + voucher.getDiscountAmount() + "," + voucher.getVoucherType());
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
