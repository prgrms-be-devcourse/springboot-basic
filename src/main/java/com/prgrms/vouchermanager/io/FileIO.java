package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.*;
import com.prgrms.vouchermanager.exception.FileIOException;
import com.prgrms.vouchermanager.util.VoucherFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileIO {
    private final String filePath;


    public FileIO(String filePath) {
        this.filePath = filePath;
    }

    public void updateFile(Map<UUID, Voucher> vouchers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            vouchers.forEach((key, voucher) -> {
                UUID id = voucher.getId();
                String type = voucher instanceof FixedAmountVoucher ? "fixed" : "percent";
                long discount = voucher.getDiscount();

                try {
                    bw.write(id + "," + type + "," + discount + "\n");
                } catch (IOException e) {
                    RuntimeException ex = new FileIOException(bw);
                    log.error(ex.getMessage());
                    throw ex;
                }
            });
        } catch (IOException e) {
            RuntimeException ex = new FileIOException(this);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void readCustomerFile(Map<UUID, Customer> customerMap) {
            try (BufferedReader bf = new BufferedReader(new FileReader(filePath))){
                String line = "";
                while((line = bf.readLine()) != null) {
                    putToCustomerMap(customerMap, line);
                }
            } catch (IOException e) {
                RuntimeException ex = new FileIOException(e);
                log.error(ex.getMessage());
                throw ex;
            }
    }

    private void putToCustomerMap(Map<UUID, Customer> customerMap, String line) {
        String[] split = line.split(",");
        Customer customer
                = new Customer(UUID.fromString(split[0]), split[1], Integer.parseInt(split[2]), true);
        customerMap.put(UUID.fromString(split[0]), customer);
    }

    public void fileToVoucherMap(Map<UUID, Voucher> vouchers) {
        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))){
            String line = "";
            while((line = bf.readLine()) != null) {
                readVoucherFile(vouchers, line);
            }
        } catch (IOException e) {
            RuntimeException ex = new FileIOException(e);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    private void readVoucherFile(Map<UUID, Voucher> vouchers, String line) {
        String[] split = line.split(",");
        UUID id = UUID.fromString(split[0]);
        long discount = Long.parseLong(split[2]);
        VoucherType type = VoucherType.of(split[1]);//VoucherType 형태로 변경Voucher voucher = voucherFactory.create(type, discount);

        Voucher voucher = VoucherFactory.create(type, discount).get();
        vouchers.put(id, voucher);
    }
}
