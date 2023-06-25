package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.util.Converter;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository{

    private final String filePath = "src/main/resources/Voucher.csv";

    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        while ((line = reader.readLine()) != null) {
            String[] record = Converter.stringToArray(line, ",");
            if (record[0].equals(voucherId)) {
                return Optional.of(Converter.stringArrToVoucher(record));
            }
        }
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        FileWriter writer = new FileWriter(filePath, true);
        String voucherString = Converter.voucherToString(voucher);
        writer.append(voucherString);
        writer.append("\n");
        writer.flush();
        writer.close();
        return voucher;
    }

    @Override
    public List<Voucher> findAll() throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = "";

        while ((line = reader.readLine()) != null) {
            String[] record = Converter.stringToArray(line, ",");
            vouchers.add(Converter.stringArrToVoucher(record));
        }
        return vouchers;
    }
}
