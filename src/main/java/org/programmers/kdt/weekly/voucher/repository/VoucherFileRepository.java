package org.programmers.kdt.weekly.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


@Profile("local")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
    private final File file = new File("voucher_database.csv");

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String line =
                voucher.getVoucherType() + "," + voucher.getVoucherId() + "," + voucher.getValue()
                    + "\n";
            bufferedWriter.write(line);
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("voucher insert() IOException  error ", e);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplitComma = line.split(",");
                Voucher voucher = VoucherType.findByType(lineSplitComma[0])
                    .create(UUID.fromString(lineSplitComma[1]),
                        Integer.parseInt(lineSplitComma[2]));
                vouchers.add(voucher);
            }

        } catch (IOException | NullPointerException e) {
            logger.error("voucher findAll() IOException  error ", e);
        }

        return List.copyOf(vouchers);
    }
}