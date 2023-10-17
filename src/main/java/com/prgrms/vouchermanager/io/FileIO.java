package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.FileIOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileIO {
    private final BufferedReader bf;
    private final String fileName;

    public FileIO(String fileName) {
        this.fileName = fileName;
        bf = new BufferedReader(getFileReader(fileName));
    }

    public void updateFile(Map<UUID, Voucher> vouchers) {
        try {
            BufferedWriter bw = new BufferedWriter(getFileWriter(fileName));
            vouchers.forEach((key, voucher) -> {
                UUID id = voucher.getId();
                String type = voucher instanceof FixedAmountVoucher ? "fixed" : "percent";
                long discount = voucher instanceof FixedAmountVoucher ?
                        ((FixedAmountVoucher) voucher).getAmount() : ((PercentAmountVoucher) voucher).getPercent();

                try {
                    bw.write(id + "," + type + "," + discount + "\n");
                } catch (IOException e) {
                    RuntimeException ex = new FileIOException();
                    log.error(ex.getMessage());
                    throw ex;
                }
            });
            bw.close();
        } catch (IOException e) {
            RuntimeException ex = new FileIOException();
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void fileToCustomerMap(Map<Integer, Customer> customerMap) {
        String line = "";

        while(true) {
            try {
                if ((line = bf.readLine()) == null) break;
            } catch (IOException e) {
                RuntimeException ex = new FileIOException();
                log.error(ex.getMessage());
                throw ex;
            }
            String[] split = line.split(",");
            Customer customer
                    = new Customer(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]));
            customerMap.put(Integer.parseInt(split[0]), customer);
        }
    }

    public void fileToVoucherMap(Map<UUID, Voucher> vouchers) {
        String line = "";

        while(true) {
            try {
                if ((line = bf.readLine()) == null) break;
            } catch (IOException e) {
                RuntimeException ex = new FileIOException();
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

    private FileReader getFileReader(String fileName) {
        URL resourceURL = getClass().getClassLoader().getResource(fileName);
        try {
            assert resourceURL != null;
            return new FileReader(new File(resourceURL.toURI()));
        } catch (FileNotFoundException | URISyntaxException e) {
            RuntimeException ex = new FileIOException();
            log.error(ex.getMessage());
            throw ex;
        }
    }

    private FileWriter getFileWriter(String fileName) {
        URL resourceURL = getClass().getClassLoader().getResource(fileName);
        try {
            assert resourceURL != null;
            return new FileWriter(new File(resourceURL.toURI()));
        } catch (URISyntaxException | IOException e) {
            RuntimeException ex = new FileIOException();
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
