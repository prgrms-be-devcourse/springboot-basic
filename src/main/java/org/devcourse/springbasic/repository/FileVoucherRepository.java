package org.devcourse.springbasic.repository;

import org.devcourse.springbasic.io.ErrorMsgPrinter;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("staging")
public class FileVoucherRepository implements VoucherRepository {

    @Value("${file-repo.voucher}")
    private String FILE_PATH;
    private final List<Voucher> vouchers = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream().filter(voucher -> voucher.getVoucherId() == voucherId)
                .findAny();
    }

    @Override
    public List<Voucher> findAll() {

        try (FileReader fileReader = new FileReader(FILE_PATH);
             BufferedReader reader = new BufferedReader(fileReader)){

            String data = "";
            while ((data = reader.readLine()) != null) {
                String[] fileData = data.split(",");
                Voucher voucher = fileDataToVoucher(fileData);
                vouchers.add(voucher);
            }

            return vouchers;

        } catch (IOException e) {
            ErrorMsgPrinter.print(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public UUID save(Voucher voucher) {

        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(voucher.toString());
            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            ErrorMsgPrinter.print(e.getMessage());
        }
        return voucher.getVoucherId();
    }

    private Voucher fileDataToVoucher(String[] fileData) {
        VoucherType voucherType = VoucherType.valueOf(fileData[0]);
        UUID voucherId = UUID.fromString(fileData[1]);
        long discountRate = Long.parseLong(fileData[2]);

        return voucherType.getFunctionToSelectVoucher().apply(voucherId, discountRate);
    }
}
