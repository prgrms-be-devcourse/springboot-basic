package com.programmers.repository;

import com.programmers.domain.FixedAmountVoucher;
import com.programmers.domain.PercentDiscountVoucher;
import com.programmers.domain.Voucher;
import com.programmers.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final File file;

    public FileVoucherRepository(@Value("${file.voucher.file-path}") String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void save(Voucher voucher) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(voucher.toString());
            writer.newLine();

            writer.flush();
        } catch (IOException e) {
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    voucherList.add(extractVoucher(line));
                }

                reader.close();
            }
        } catch (IOException e) {
        }
        return Collections.unmodifiableList(voucherList);
    }

    public Voucher extractVoucher(String line) {
        line = line.replace(" ", "");
        String[] voucherInfo = line.split("\\[|\\]|=|,");

        String type = voucherInfo[2].toLowerCase();
        UUID id = UUID.fromString(voucherInfo[4]);
        long discountValue = Long.parseLong(voucherInfo[6]);
        String name = voucherInfo[8];

        if (VoucherType.findVoucherTypeByName(type) == VoucherType.FixedAmountVoucher) {
            return new FixedAmountVoucher(id, name, discountValue);
        }

        return new PercentDiscountVoucher(id, name, discountValue);
    }
}
