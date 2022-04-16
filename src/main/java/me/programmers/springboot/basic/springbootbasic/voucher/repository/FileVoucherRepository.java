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
    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            Map<UUID, Voucher> voucherMap = fileInput(filename);

            for (UUID uuid : voucherMap.keySet()) {
                voucherList.add(voucherMap.get(uuid));
            }
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        }

        return voucherList;
    }

    @Override
    public void save(Voucher voucher) {
        try {
            vouchers.put(voucher.getVoucherId(), voucher);
            fileOutput(filename);
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        }
    }

    @Override
    public Map<UUID, Voucher> fileInput(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Map<UUID, Voucher> voucherMap = null;

        try {
            voucherMap = (Map<UUID, Voucher>) ois.readObject();
        } catch (ClassNotFoundException e) {
            logger.error(e.toString());
        }

        ois.close();
        fis.close();

        return voucherMap;
    }

    @Override
    public void fileOutput(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(vouchers);
        oos.flush();

        oos.close();
        fos.close();
    }
}
