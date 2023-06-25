package org.devcourse.springbasic.voucher.repository;

import org.devcourse.springbasic.voucher.FixedAmountVoucher;
import org.devcourse.springbasic.voucher.PercentDiscountVoucher;
import org.devcourse.springbasic.voucher.Voucher;
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

        try {

            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader reader = new BufferedReader(fileReader);

            String data = "";
            while ((data = reader.readLine()) != null) {

                // voucher 종류, voucher ID, voucher 할인율
                String[] fileData = data.split(",");
                Voucher voucher = fileDataToVoucher(fileData);
                vouchers.add(voucher);

            }

            return vouchers;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public UUID create(Voucher voucher) {

        try {

            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(voucher.toString());
            writer.newLine();

            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

        return voucher.getVoucherId();
    }

    private Voucher fileDataToVoucher(String[] fileData) {

        Voucher newVoucher = null;
        String voucherType = fileData[0];
        UUID voucherId = UUID.fromString(fileData[1]);
        long voucherDiscountPolicy = Long.parseLong(fileData[2]);

        if (voucherType.equals(FixedAmountVoucher.class.getSimpleName())) {
            newVoucher = new FixedAmountVoucher(voucherId, voucherDiscountPolicy);

        } else if (voucherType.equals(PercentDiscountVoucher.class.getSimpleName())) {
            newVoucher = new PercentDiscountVoucher(voucherId, voucherDiscountPolicy);
        }

        return newVoucher;
    }
}
