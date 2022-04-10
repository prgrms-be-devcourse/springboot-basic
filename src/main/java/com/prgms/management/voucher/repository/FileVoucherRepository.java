package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.FixedAmountVoucher;
import com.prgms.management.voucher.entity.PercentDiscountVoucher;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import com.prgms.management.voucher.exception.VoucherListEmptyException;
import com.prgms.management.voucher.exception.VoucherNotFoundException;
import com.prgms.management.voucher.exception.VoucherNotSaveException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
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
    public Voucher findById(UUID voucherId) throws VoucherException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String item;
            while ((item = bufferedReader.readLine()) != null) {
                String[] array = item.split(",");
                if (voucherId.equals(UUID.fromString(array[1]))) {
                    if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                        return new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2]));
                    } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                        return new FixedAmountVoucher(UUID.fromString(array[1]), Long.parseLong(array[2]));
                    }
                }
            }
        } catch (IOException e) {
            throw new VoucherNotFoundException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new VoucherNotFoundException();
            }
        }
        throw new VoucherNotFoundException();
    }

    @Override
    public List<Voucher> findAll() throws VoucherException {
        List<Voucher> vouchers = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String item;
            while ((item = bufferedReader.readLine()) != null) {
                String[] array = item.split(",");
                if (array[0].equals(PercentDiscountVoucher.class.getCanonicalName())) {
                    vouchers.add(new PercentDiscountVoucher(UUID.fromString(array[1]), Integer.parseInt(array[2])));
                } else if (array[0].equals(FixedAmountVoucher.class.getCanonicalName())) {
                    vouchers.add(new FixedAmountVoucher(UUID.fromString(array[1]), Long.parseLong(array[2])));
                } else {
                    throw new VoucherNotFoundException();
                }
            }
        } catch (IOException e) {
            throw new VoucherListEmptyException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
               throw new VoucherListEmptyException();
            }
        }
        return vouchers;
    }

    @Override
    public Voucher save(Voucher voucher) throws VoucherException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(voucher.getStringForCSV());
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new VoucherNotSaveException();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                throw new VoucherNotSaveException();
            }
        }
        return voucher;
    }
}