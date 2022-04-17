package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.io.FileInput;
import me.programmers.springboot.basic.springbootbasic.io.FileOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository, FileInput, FileOutput {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String filename = "voucherList.dat";
    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();

        fileInput(filename);
        for (UUID uuid : vouchers.keySet()) {
            voucherList.add(vouchers.get(uuid));
        }

        return voucherList;
    }

    @Override
    public void save(Voucher voucher) {
        fileOutput(filename, voucher);
    }

    @Override
    public void fileInput(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            Voucher voucher = (Voucher) ois.readObject();
            vouchers.put(voucher.getVoucherId(), voucher);
        } catch (ClassNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e2) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {}
        }
    }

    @Override
    public void fileOutput(String fileName, Voucher voucher) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(fileName, true);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(voucher);
            oos.flush();
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {}
        }


    }
}
