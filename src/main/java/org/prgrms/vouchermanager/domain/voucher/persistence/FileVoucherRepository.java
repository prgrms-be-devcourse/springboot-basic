package org.prgrms.vouchermanager.domain.voucher.persistence;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.prgrms.vouchermanager.exception.IllegalResourceAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

/**
 * Voucher를 직렬화하여 Voucher마다 한개의 파일로 저장하고 역직렬화해서 읽어오는 리포지토리입니다.
 */
@Repository
@Profile("file")
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
                .filter(voucher -> voucher.getId().equals(voucherId))
                .findFirst();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String savePath = new StringBuilder()
                .append(voucherFilePath)
                .append(File.separator)
                .append(voucher.getId())
                .toString();

        if (new File(savePath).exists()) throw new IllegalArgumentException();

        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(voucher);

        } catch (IOException e) {
            log.error(savePath + " " + voucher);
            throw new IllegalResourceAccessException("voucher를 insert하는 과정에서 문제가 발생했습니다.\n" + e.getMessage());
        }

        return voucher;
    }

    private List<Voucher> getVouchers() {
        File[] files = new File(voucherFilePath).listFiles();
        if (files == null) return Collections.emptyList();

        List<Voucher> vouchers = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            try (FileInputStream fileInputStream = new FileInputStream(file.getPath());
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                vouchers.add((Voucher) objectInputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalResourceAccessException("voucher를 조회하는 과정에서 문제가 발생했습니다.\n" + e.getMessage());
            }
        });

        return vouchers;
    }
}
