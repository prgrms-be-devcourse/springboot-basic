package org.prgrms.springbootbasic.engine.repository;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private final String path = "vouchers" + File.separator;

    @Override
    public Voucher insert(Voucher voucher) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + voucher.getVoucherId() + ".txt"))) {
            bos.write(voucher.toFileString().getBytes());
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
            Optional<File[]> files = Optional.ofNullable(dir.listFiles());
            if(files.isPresent()) {
                for (File file:files.get()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    UUID voucherId = UUID.fromString(br.readLine());
                    VoucherType type = VoucherType.valueOf(br.readLine());
                    Integer value = Integer.parseInt(br.readLine());
                    LocalDateTime createdAt = LocalDateTime.parse(br.readLine());
                    br.close();
                    Voucher voucher = type.createVoucher(voucherId, value, createdAt);
                    vouchers.add(voucher);
                }
            }
        } catch(Exception ex) {
            throw new VoucherException("Broken voucher file problem. Please call developer");
        }
        return vouchers;
    }

    @Override
    public int count() {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            File dir = new File(path);
            if(!dir.exists()) {
                return 0;
            }
            Optional<File[]> files = Optional.ofNullable(dir.listFiles());
            return files.isEmpty() ? 0 : files.get().length;
        } catch(Exception ex) {
            throw new VoucherException("Broken voucher file problem. Please call developer");
        }
    }
}
