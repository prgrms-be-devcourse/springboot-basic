package org.prgrms.part1.engine;

import org.prgrms.part1.exception.FileException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository{
    private final String path = "vouchers" + File.separator;

    @Override
    public Voucher insert(Voucher voucher) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + voucher.getVoucherId() + ".txt"))) {
            bos.write(voucher.getVoucherId().toString().getBytes());
            bos.write("\n".getBytes());
            bos.write(voucher.getVoucherType().toString().getBytes());
            bos.write("\n".getBytes());
            bos.write(voucher.getValue().toString().getBytes());
        } catch (IOException ex) {
            throw new FileException("Voucher file creation problem. Please call developer");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            File dir = new File(path);
            if(!dir.exists()) {
                return vouchers;
            }
            File[] files = dir.listFiles();
            if(files != null) {
                for (File file:files) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    UUID voucherId = UUID.fromString(br.readLine());
                    VoucherType type = VoucherType.valueOf(br.readLine());
                    int value = Integer.parseInt(br.readLine());
                    br.close();
                    Optional<Voucher> voucher = convertFileToVoucher(voucherId, type, value);
                    if (voucher.isEmpty()) {
                        throw new FileException("Invalid voucher type detected. Please call developer");
                    } else {
                        vouchers.add(voucher.get());
                    }
                }
            }
        } catch(Exception ex) {
            throw new FileException("Broken voucher file problem. Please call developer");
        }

        return vouchers;
    }

    private Optional<Voucher> convertFileToVoucher(UUID voucherId, VoucherType voucherType, int value) {
        if (voucherType.equals(VoucherType.FixedAmount)) {
            return Optional.of(new FixedAmountVoucher(voucherId, value));
        } else if (voucherType.equals(VoucherType.PercentDiscount)) {
            return Optional.of(new PercentDiscountVoucher(voucherId, value));
        } else {
            return Optional.empty();
        }
    }
}
