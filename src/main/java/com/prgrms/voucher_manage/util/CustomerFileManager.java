package com.prgrms.voucher_manage.util;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileManager{

    private final Map<UUID, Customer> customerStorage = new ConcurrentHashMap<>();
    private final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    public Map<UUID, Customer> loadCustomerData(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(UUID.fromString(data[0]), data[1]);
                customerStorage.put(UUID.fromString(data[0]), customer);
            }
        }  catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customerStorage;
    }

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
}
