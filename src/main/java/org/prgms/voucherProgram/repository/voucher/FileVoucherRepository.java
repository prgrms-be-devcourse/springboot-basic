package org.prgms.voucherProgram.repository.voucher;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

    public static final String FILE_LOCATION = "./src/main/java/org/prgms/voucherProgram/repository/filedata";
    public static final String FILE_NAME = FILE_LOCATION + "/voucherData.txt";
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 voucher 파일이 아닙니다.";

    static {
        File file = new File(FILE_LOCATION);
        file.mkdirs();
    }

    @Override
    public Voucher save(Voucher voucher) {
        saveVoucherAtFile(voucher);
        return voucher;
    }

    private void saveVoucherAtFile(Voucher voucher) {
        try (
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME, true))
        ) {
            objectOutputStream.writeObject(voucher);
        } catch (IOException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME)
        ) {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Voucher voucher = (Voucher)objectInputStream.readObject();
                vouchers.add(voucher);
            }
        } catch (EOFException e) {
            return vouchers;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }
}
