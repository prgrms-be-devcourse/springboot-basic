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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static Integer fixedVoucherCount = 0;
    private static Integer percentVoucherCount = 0;
    private final File fixedVoucherStorage = new File("FixedVoucherDB.ser");
    private final File percentVoucherStorage = new File("PercentVoucherDB.ser");

    @Override
    public void save(Voucher voucher) {

        if (voucher.getClass() == FixedAmountVoucher.class) {
            FixedAmountVoucher fixedAmountVoucher = (FixedAmountVoucher) voucher;
            try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(fixedVoucherStorage, true))) {
                stream.writeObject(fixedAmountVoucher);
                ++fixedVoucherCount;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (voucher.getClass() == PercentDiscountVoucher.class) {
            PercentDiscountVoucher percentDiscountVoucher = (PercentDiscountVoucher) voucher;
            try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(percentVoucherStorage, true))) {
                stream.writeObject(percentDiscountVoucher);
                ++percentVoucherCount;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Voucher> findAll() {
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

        try (FileInputStream fixedVoucherFileStream = new FileInputStream(
            fixedVoucherStorage)) {
            while (true) {
                ObjectInputStream stream = new ObjectInputStream(fixedVoucherFileStream);
                FixedAmountVoucher voucher = (FixedAmountVoucher) stream.readObject();
                ++voucherCount;
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
                ++voucherCount;
            }
        } catch (EOFException ex) {
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
            fixedVoucherCount = 0;
            percentVoucherCount = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
