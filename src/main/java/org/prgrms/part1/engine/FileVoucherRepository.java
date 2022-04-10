package org.prgrms.part1.engine;

import org.prgrms.part1.exception.FileException;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository{
    private final String path = new File("").getAbsolutePath() + "\\vouchers\\";
    private final List<UUID> vouchers = new ArrayList<>();

    @Override
    public Voucher insert(Voucher voucher) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdir();
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + voucher.getVoucherId() + ".txt"))) {
            bos.write(voucher.getVoucherId().toString().getBytes());
            bos.write("\n".getBytes());
            bos.write(voucher.getVoucherType().toString().getBytes());
            bos.write("\n".getBytes());
            bos.write(voucher.getDiscount().toString().getBytes());
        } catch (IOException ex) {
            throw new FileException();
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
                    String type = br.readLine();
                    int discount = Integer.parseInt(br.readLine());
                    if (type.equals("FixedAmount")) {
                        vouchers.add(new FixedAmountVoucher(voucherId, discount));
                    } else if (type.equals("PercentDiscount")) {
                        vouchers.add(new PercentDiscountVoucher(voucherId, discount));
                    } else {
                        br.close();
                        throw new FileException();
                    }
                    br.close();
                }
            }
        } catch(Exception ex) {
            throw new FileException();
        }

        return vouchers;
    }
}
