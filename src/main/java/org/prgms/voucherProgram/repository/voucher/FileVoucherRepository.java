package org.prgms.voucherProgram.repository.voucher;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    public static final String FILE_NAME = "src/main/resources/file/voucherData.txt";
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 voucher 파일이 아닙니다.";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Voucher save(Voucher voucher) {
        saveVoucherAtFile(voucher);
        logger.info("Voucher save at file => {}", voucher);
        return voucher;
    }

    private void saveVoucherAtFile(Voucher voucher) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME, true))) {
            objectOutputStream.writeObject(voucher);
        } catch (IOException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME)) {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Voucher voucher = (Voucher)objectInputStream.readObject();
                vouchers.add(voucher);
            }
        } catch (EOFException | FileNotFoundException e) {
            logger.info("Voucher read at file => {}", vouchers);
            return vouchers;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }
}
