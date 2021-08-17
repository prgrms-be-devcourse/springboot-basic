package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public FileVoucherRepository() throws IOException {
        File file = new File("voucher.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        writer = new BufferedWriter(new FileWriter(file, true));
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(" ");
                if (s[1].equals(voucherId.toString())) {
                    return Optional.of(createConcreteVoucher(s));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        String line;
        List<Voucher> list = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                String[] voucherText = line.split(" ");
                list.add(createConcreteVoucher(voucherText));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Voucher createConcreteVoucher(String[] voucherText) {
        if ("FixedAmountVoucher".equals(voucherText[0])) {
            return new FixedAmountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        } else {
            return new PercentDiscountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.getClass().getSimpleName() + " " + voucher.getVoucherId().toString() + " " + voucher.getValue());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }
}
