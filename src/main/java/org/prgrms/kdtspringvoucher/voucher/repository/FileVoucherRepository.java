package org.prgrms.kdtspringvoucher.voucher.repository;

import org.prgrms.kdtspringvoucher.aop.TrackTime;
import org.prgrms.kdtspringvoucher.voucher.service.FixedAmountVoucher;
import org.prgrms.kdtspringvoucher.voucher.service.PercentDiscountVoucher;
import org.prgrms.kdtspringvoucher.voucher.service.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
@Profile({"local", "default"})
public class FileVoucherRepository implements VoucherRepository {
    private final File file;

    public FileVoucherRepository(@Value("${database.file.voucher}") String filename) {
        file = new File(filename);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                if (voucherId.equals(UUID.fromString(array[1]))) {
                    if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                        return new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2]));
                    } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                        return new FixedAmountVoucher(UUID.fromString(array[1]), Long.parseLong(array[2]));
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                    vouchers.add(new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2])));
                } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                    vouchers.add(new FixedAmountVoucher(UUID.fromString(array[1]), Long.parseLong(array[2])));
                } else {
                    return Collections.emptyList();
                }
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return vouchers;
    }

    @Override
    @TrackTime
    public Voucher save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(voucher.getStringForCSV());
            bufferedWriter.newLine();
        } catch (IOException e) {
            return null;
        }
        return voucher;
    }
}
