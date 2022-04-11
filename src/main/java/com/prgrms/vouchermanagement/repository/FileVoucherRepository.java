package com.prgrms.vouchermanagement.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 직렬화를 사용하여 voucher 리스트를 저장하고 조회한다.
 */
@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    public static final String VOUCHERS_FILE_NAME = "vouchers.ser";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(Voucher voucher) {
        List<Voucher> vouchers = findAll();

        try (
                FileOutputStream fos = new FileOutputStream(VOUCHERS_FILE_NAME);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos =new ObjectOutputStream(bos);
        ) {
            vouchers.add(voucher);
            oos.writeObject(vouchers);
            log.info("voucher is saved to file - {}", voucher);
        } catch (IOException e) {
            log.error("failed to create {}", VOUCHERS_FILE_NAME, e);
        }
    }

    /**
     * 파일을 삭제합니다.
     * 테스트 실행시 초기화 용으로 사용
     */
    public void clear() {
        File file = new File(VOUCHERS_FILE_NAME);
        file.delete();
    }

    /**
     * 역직렬화를 통해 voucher 리스트를 가져온다.
     * 저장된 바우처가 없어 파일이 존재하지 않는 경우 비어있는 리스트를 반환한다.
     */
    @Override
    public List<Voucher> findAll() {
        try (
                FileInputStream fis = new FileInputStream(VOUCHERS_FILE_NAME);
                BufferedInputStream bis =new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
        ){
            List<Voucher> vouchers = (List<Voucher>) ois.readObject();
            log.info("find all vouchers from file. size={}", vouchers.size());
            return vouchers;
        } catch (IOException e) {
            log.info("not found vouchers.scv");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
