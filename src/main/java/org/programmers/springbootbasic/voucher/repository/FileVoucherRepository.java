package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final File file = new File("voucher_file_database.csv");

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(" ");
                if (voucherInfo[2].equals(voucherId.toString())) {
                    if (voucherInfo[0].equals("FixedAmountVoucher")) {
                        return Optional.of(new FixedAmountVoucher(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4])));
                    }
                    if (voucherInfo[0].equals("PercentDiscountVoucher")) {
                        return Optional.of(new PercentDiscountVoucher(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4])));
                    }
                }
            }
        } catch (IOException e) {
            logger.error("{0} findAll IO exception error", e);
        }
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try (
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("{0} insert IO exception error", e);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        String line;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(" ");
                if (voucherInfo[0].equals("FixedAmountVoucher")) {
                    FixedAmountVoucher voucher =
                            new FixedAmountVoucher(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4]));
                    voucherList.add(voucher);
                } else if (voucherInfo[0].equals("PercentDiscountVoucher")) {
                    PercentDiscountVoucher voucher =
                            new PercentDiscountVoucher(UUID.fromString(voucherInfo[2]), Long.parseLong(voucherInfo[4]));
                    voucherList.add(voucher);
                }
            }
        } catch (IOException e) {
            logger.error("{0} findAll IO exception error", e);
        }
        return voucherList;
    }
}


