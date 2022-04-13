package org.prgrms.vouchermanager.voucher.repository;

import org.prgrms.vouchermanager.exception.IllegalResourceAccessException;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;

@Repository
@Profile("option")
public class FileVoucherRepository implements VoucherRepository {
    @Value("${voucher.path}")
    private String voucherFilePath;
    private File[] files;
    private final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository() {
    }

    public FileVoucherRepository(String voucherFilePath) {
        this.voucherFilePath = voucherFilePath;
    }

    @PostConstruct
    public void init() {
        File filePath = new File(voucherFilePath);
        if (!filePath.exists()) filePath.mkdirs();
    }

    @Override
    public List<Voucher> getAll() {
        return getVouchers();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = getVouchers();
        return vouchers.stream().filter(voucher -> voucher.getVoucherId().equals(voucherId)).findFirst();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String savePath = new StringBuilder().append(voucherFilePath).append(File.separator).append(voucher.getVoucherId()).toString();

        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(voucher);

        } catch (IOException e) {
            log.error(savePath + " " + voucher);
            throw new IllegalResourceAccessException();
        }

        return voucher;
    }

    private List<Voucher> getVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        files = new File(voucherFilePath).listFiles();

        if (files == null) return vouchers;

        Arrays.stream(files).forEach(file -> {
            try (FileInputStream fileInputStream = new FileInputStream(file.getPath());
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                vouchers.add((Voucher) objectInputStream.readObject());

            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalResourceAccessException();
            }
        });

        return vouchers;
    }

}
