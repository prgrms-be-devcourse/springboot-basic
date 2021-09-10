package prgms.springbasic.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import prgms.springbasic.voucher.FixedAmountVoucher;
import prgms.springbasic.voucher.PercentDiscountVoucher;
import prgms.springbasic.voucher.Voucher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String path = "src/main/VoucherData.csv";
    private static final int TYPE = 0;
    private static final int DISCOUNT_VALUE = 1;
    private static final int VOUCHER_ID = 2;
    private final File file;

    public FileVoucherRepository() {
        file = new File(path);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] voucherInfo = line.split(", ");

                if (voucherInfo[VOUCHER_ID].equals(String.valueOf(voucherId))) {
                    return Optional.of(makeVoucher(voucherInfo));
                }
            }
            reader.close();

        } catch (IOException exception) {
            logger.error(String.valueOf(exception));
        }
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(voucher.toString() + System.lineSeparator());
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException exception) {
            logger.error(String.valueOf(exception));
        }
        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] voucherInfo = line.split(", ");
                voucherList.add(makeVoucher(voucherInfo));
            }
            reader.close();

        } catch (IOException exception) {
            logger.error(String.valueOf(exception));
        }
        return voucherList;
    }

    private static Voucher makeVoucher(String[] voucherInfo) {
        if (voucherInfo[TYPE].equals(FixedAmountVoucher.class.getSimpleName())) {
            new FixedAmountVoucher(UUID.fromString(voucherInfo[VOUCHER_ID]), Long.parseLong(voucherInfo[DISCOUNT_VALUE]));
        }
        return new PercentDiscountVoucher(UUID.fromString(voucherInfo[VOUCHER_ID]), Long.parseLong(voucherInfo[DISCOUNT_VALUE]));
    }
}
