package org.prgrms.part1.engine;

import org.prgrms.part1.exception.VoucherException;
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
            throw new VoucherException("Voucher file creation problem. Please call developer");
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
                    Long value = Long.parseLong(br.readLine());
                    br.close();
                    Voucher voucher = type.createVoucher(voucherId, value);
                    vouchers.add(voucher);
                }
            }
        } catch(Exception ex) {
            throw new VoucherException("Broken voucher file problem. Please call developer");
        }
        return vouchers;
    }
}
