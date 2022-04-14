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
import java.util.*;

@Repository
@Profile("option")
public class FileVoucherRepository implements VoucherRepository {
    private final String voucherFilePath;

    private final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository(@Value("${voucher.path}") String voucherFilePath) {
        this.voucherFilePath = voucherFilePath;
    }

    @PostConstruct
    public void init() {
        File filePath = new File(voucherFilePath);
        if (!filePath.exists())
            filePath.mkdirs();
    }

    @Override
    public List<Voucher> getAll() {
        return getVouchers();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return getVouchers().stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }


    @Override
    public Voucher insert(Voucher voucher) {
        String savePath = new StringBuilder().append(voucherFilePath).append(File.separator).append(voucher.getVoucherId()).toString();

        if (new File(savePath).exists())
            throw new IllegalArgumentException();

        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(voucher);

        } catch (IOException e) {
            log.error(savePath + " " + voucher);
            // RuntimeException 중에서 의미가 적절한게 없는 것 같아서 RuntimeException을 상속받는 예외 정의하였습니다.
            throw new IllegalResourceAccessException();
        }

        return voucher;
    }

    private List<Voucher> getVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        File[] files = new File(voucherFilePath).listFiles();

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
