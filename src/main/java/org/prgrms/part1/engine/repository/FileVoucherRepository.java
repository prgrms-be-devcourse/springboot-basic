package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.exception.VoucherException;
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
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteAll() {

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
        return 0;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return null;
    }
}
