package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.exception.ExceptionMessage;
import org.prgrms.kdt.exception.VoucherNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository {

    private final String filePath = "voucher.txt";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String voucherIdOfLine = line.split("\\|")[1];
                if (!voucherIdOfLine.equals(voucherId.toString())) {
                    continue;
                }
                return Optional.of(getVoucherFrom(line));
            }
        } catch (FileNotFoundException e) {
            throw new VoucherNotFoundException(ExceptionMessage.VOUCHER_FILE_NOT_FOUNDED.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        createFileIfNotExist();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(getVoucherInfo(voucher));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                vouchers.add(getVoucherFrom(line));
            }
        } catch (FileNotFoundException e) {
            throw new VoucherNotFoundException(ExceptionMessage.VOUCHER_FILE_NOT_FOUNDED.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return vouchers;
    }

    private Voucher getVoucherFrom(String line){
        String[] elements = line.split("\\|");
        if (VoucherType.findByVoucherType(elements[0]) == VoucherType.FIXED_AMOUNT) {
            return new FixedAmountVoucher(UUID.fromString(elements[1]), Long.valueOf(elements[2]));
        }

        return new PercentDiscountVoucher(UUID.fromString(elements[1]), Long.valueOf(elements[2]));
    }

    private void createFileIfNotExist()
    {
        File voucherFile = new File(filePath);
        try {
            voucherFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getVoucherInfo(Voucher voucher){
        String pattern = "{0}|{1}|{2}";

        if (voucher instanceof FixedAmountVoucher) {
            return MessageFormat.format(pattern,
                    VoucherType.FIXED_AMOUNT.getVoucherType(),
                    voucher.getVoucherId().toString(),
                    Long.toString(((FixedAmountVoucher) voucher).getAmount())
            );
        }

        return MessageFormat.format(pattern,
                VoucherType.PERCENT_DISCOUNT.getVoucherType(),
                voucher.getVoucherId().toString(),
                Long.toString(((PercentDiscountVoucher) voucher).getPercent())
        );
    }

    public void removeAllVouchers() {
        File file = new File(filePath);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
