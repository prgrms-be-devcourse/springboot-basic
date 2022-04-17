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
import java.util.ArrayList;
import java.util.List;

/**
 * 직렬화를 사용하여 voucher 리스트를 저장하고 조회한다.
 */
@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private List<Voucher> vouchers;
    private final FilePathProperties filePathProperties;

    public FileVoucherRepository(FilePathProperties filePathProperties) {
        this.filePathProperties = filePathProperties;
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.add(voucher);
        log.info("voucher is saved to file - {}", voucher);
    }

    @Override
    public List<Voucher> findAll() {
        log.info("find all vouchers from file. size={}", vouchers.size());
        return new ArrayList<>(vouchers);
    }

    @PostConstruct
    private void init() {
        String vouchersFilePath = filePathProperties.getVouchersFilePath();
        try (
                FileInputStream fis = new FileInputStream(vouchersFilePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
        ) {
            vouchers = (List<Voucher>) ois.readObject();
        } catch (IOException e) {
            log.error("failed to create or find {}", vouchersFilePath, e);

            //파일을 읽는데 실패한 경우 빈 리스트로 초기화
            vouchers = new ArrayList<>();
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
            oos.writeObject(vouchers);
        } catch (IOException e) {
            log.error("failed to create or find {}", vouchersFilePath, e);
        }
    }
}
