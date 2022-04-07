package org.prgrms.springbootbasic.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;

public class FileVoucherRepository implements VoucherRepository {

    private static Integer fixedVoucherCount = 0;
    private static Integer percentVoucherCount = 0;
    private final File fixedVoucherStorage = new File("FixedVoucherDB.ser");
    private final File PercentVoucherStorage = new File("PercentVoucherDB.ser");

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
                new FileOutputStream(PercentVoucherStorage, true))) {
                stream.writeObject(percentDiscountVoucher);
                ++percentVoucherCount;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Integer getVoucherTotalNumber() {
        return fixedVoucherCount + percentVoucherCount;
    }

    @Override
    public void removeAll() {
        try {
            new FileOutputStream(fixedVoucherStorage).close();
            new FileOutputStream(PercentVoucherStorage).close();
            fixedVoucherCount = 0;
            percentVoucherCount = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
