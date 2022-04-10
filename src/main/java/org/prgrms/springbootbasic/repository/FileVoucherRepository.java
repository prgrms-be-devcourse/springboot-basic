package org.prgrms.springbootbasic.repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String FIXED_VOUCHER_DB_SER = "filedb/FixedVoucherDB.ser";
    private static final String PERCENT_VOUCHER_DB_SER = "filedb/PercentVoucherDB.ser";
    private final File fixedVoucherStorage = new File(FIXED_VOUCHER_DB_SER);
    private final File percentVoucherStorage = new File(PERCENT_VOUCHER_DB_SER);

    @Override
    public void save(Voucher voucher) {
        logger.info("FileVoucherRepository.save() called");

        if (voucher instanceof FixedAmountVoucher) {
            try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(fixedVoucherStorage, true))) {
                stream.writeObject(voucher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (voucher instanceof PercentDiscountVoucher) {
            try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(percentVoucherStorage, true))) {
                stream.writeObject(voucher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Voucher> findAll() {
        logger.info("FileVoucherRepository.findAll() called");

        List<Voucher> vouchers = new ArrayList<>();

        try (FileInputStream fixedVoucherFileStream = new FileInputStream(
            fixedVoucherStorage)) {
            while (true) {
                ObjectInputStream stream = new ObjectInputStream(fixedVoucherFileStream);
                FixedAmountVoucher voucher = (FixedAmountVoucher) stream.readObject();
                vouchers.add(voucher);
            }
        } catch (EOFException of) {
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try (FileInputStream percentVoucherFileStream = new FileInputStream(
            percentVoucherStorage)) {
            while (true) {
                ObjectInputStream stream = new ObjectInputStream(percentVoucherFileStream);
                PercentDiscountVoucher voucher = (PercentDiscountVoucher) stream.readObject();
                vouchers.add(voucher);
            }
        } catch (EOFException ex) {
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return vouchers;
    }

    @Override
    public Integer getVoucherTotalNumber() {
        int voucherCount = 0;

        voucherCount += countVoucherNumber(fixedVoucherStorage);
        voucherCount += countVoucherNumber(percentVoucherStorage);

        return voucherCount;
    }

    private int countVoucherNumber(File fixedVoucherStorage) {
        int voucherCount = 0;

        try (FileInputStream fileInputStream = new FileInputStream(
            fixedVoucherStorage)) {
            while (true) {
                ObjectInputStream stream = new ObjectInputStream(fileInputStream);
                stream.readObject();
                ++voucherCount;
            }
        } catch (EOFException of) {
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return voucherCount;
    }

    @Override
    public void removeAll() {
        try {
            new FileOutputStream(fixedVoucherStorage).close();
            new FileOutputStream(percentVoucherStorage).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
