package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.util.FilePathProperties;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 직렬화를 사용하여 voucher 리스트를 저장하고 조회한다.
 */
@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<Long, Voucher> store;
    private long sequence;
    private final FilePathProperties filePathProperties;

    public FileVoucherRepository(FilePathProperties filePathProperties) {
        this.filePathProperties = filePathProperties;
    }

    @Override
    public Long save(Voucher voucher) {
        if (voucher == null) {
            return -1L;
        }

        Long voucherId = ++sequence;
        store.put(voucherId, voucher);
        return voucherId;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
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
    public void remove(Long voucherId) {
        if (voucherId == null) {
            return;
        }

        store.remove(voucherId);
    }

    @PostConstruct
    private void init() {
        String vouchersFilePath = filePathProperties.getVouchersFilePath();
        sequence = getSequence();
        store = getVoucherStoreMap(vouchersFilePath);
    }

    @PreDestroy
    private void destroy() {
        storeVoucherMap();
        storeSequence();
    }

    private Map<Long, Voucher> getVoucherStoreMap(String vouchersFilePath) {
        try (
                FileInputStream fis = new FileInputStream(vouchersFilePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
        ) {
            return (Map<Long, Voucher>) ois.readObject();
        } catch (IOException e) {
            log.error("failed to create or find {}", vouchersFilePath, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //파일을 읽는데 실패한 경우 빈 리스트로 초기화
        return new HashMap<>();
    }

    private long getSequence() {
        String sequenceFilePath = filePathProperties.getSequenceStorePath();
        try (FileReader fr = new FileReader(sequenceFilePath)){
            StringBuilder sb = new StringBuilder();
            int read;
            while ((read = fr.read()) != -1) {
                sb.append((char) read);
            }

            return Long.parseLong(sb.toString());
        } catch (IOException e) {
            return 0L;
        }
    }

    private void storeVoucherMap() {
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

    private void storeSequence() {
        String sequenceStorePath = filePathProperties.getSequenceStorePath();
        try (FileWriter fw = new FileWriter(sequenceStorePath, false)) {
            fw.write(String.valueOf(sequence));
        } catch (IOException e) {
            log.error("failed to create or find {}", sequenceStorePath, e);
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Voucher> findVoucherByCustomer(Long customerId) {
        return null;
    }
}
