package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.FileIOException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileIO {
    private final BufferedReader bf;
    private final String filePath;

    public FileIO(String filePath) {
        this.filePath = filePath;
        try {
            bf = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            FileIOException ex = new FileIOException(this);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void updateFile(Map<UUID, Voucher> vouchers) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
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
            bw.close();
        } catch (IOException e) {
            RuntimeException ex = new FileIOException(this);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void fileToCustomerMap(Map<UUID, Customer> customerMap) {
        String line = "";

        while(true) {
            try {
                if ((line = bf.readLine()) == null) break;
            } catch (IOException e) {
                RuntimeException ex = new FileIOException(bf);
                log.error(ex.getMessage());
                throw ex;
            }
            String[] split = line.split(",");
            Customer customer
                    = new Customer(UUID.fromString(split[0]), split[1], Integer.parseInt(split[2]), true);
            customerMap.put(UUID.fromString(split[0]), customer);
        }
    }

    public void fileToVoucherMap(Map<UUID, Voucher> vouchers) {
        String line = "";

        while(true) {
            try {
                if ((line = bf.readLine()) == null) break;
            } catch (IOException e) {
                RuntimeException ex = new FileIOException(bf);
                log.error(ex.getMessage());
                throw ex;
            }
            String[] split = line.split(",");
            Voucher voucher = null;
            UUID id = UUID.fromString(split[0]);
            long discount = Long.parseLong(split[2]);

            if(split[1].equals("fixed")) voucher = new FixedAmountVoucher(id, discount);
            else if(split[1].equals("percent")) voucher = new PercentAmountVoucher(id, discount);

            vouchers.put(id, voucher);
        }
    }
}
