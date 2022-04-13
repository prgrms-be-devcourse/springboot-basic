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
    private BufferedWriter bufferedWriter = getBufferWriter(file);
    private BufferedReader bufferedReader;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("insert IO exception error");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        String line;
        try {
            bufferedReader = getBufferedReader(file);
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
            logger.error("find IO exception error");
        }
        return voucherList;
    }

    private BufferedReader getBufferedReader(File file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException error");
        }
        return bufferedReader;
    }

    private BufferedWriter getBufferWriter(File file) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            logger.error("IO exception error");
        }
        return bufferedWriter;
    }
}


