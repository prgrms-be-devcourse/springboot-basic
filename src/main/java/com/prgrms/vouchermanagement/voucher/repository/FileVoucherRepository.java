package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.util.FilePathProperties;
import com.prgrms.vouchermanagement.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;

/**
 * 직렬화를 사용하여 voucher 리스트를 저장하고 조회한다.
 */
@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<UUID, Voucher> store;
    private final FilePathProperties filePathProperties;

    public FileVoucherRepository(FilePathProperties filePathProperties) {
        this.filePathProperties = filePathProperties;
    }

    @Override
    public void save(Voucher voucher) {
        if (voucher == null) {
            return;
        }

        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (voucherId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public void update(Voucher voucher) {
        if (voucher == null) {
            return;
        }

        if (store.containsKey(voucher.getVoucherId())) {
            store.put(voucher.getVoucherId(), voucher);
        }
    }

    @Override
    public void remove(Voucher voucher) {
        if (voucher == null) {
            return;
        }

        store.remove(voucher.getVoucherId());
    }

    @PostConstruct
    private void init() {
        String vouchersFilePath = filePathProperties.getVouchersFilePath();
        try (
                FileInputStream fis = new FileInputStream(vouchersFilePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
        ) {
            store = (Map<UUID, Voucher>) ois.readObject();
        } catch (IOException e) {
            log.error("failed to create or find {}", vouchersFilePath, e);

            //파일을 읽는데 실패한 경우 빈 리스트로 초기화
            store = new HashMap<>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        String vouchersFilePath = filePathProperties.getVouchersFilePath();
        try (
                FileOutputStream fos = new FileOutputStream(vouchersFilePath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
        ) {
            oos.writeObject(store);
        } catch (IOException e) {
            log.error("failed to create or find {}", vouchersFilePath, e);
        }
    }
}
