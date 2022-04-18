package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.io.FileInput;
import me.programmers.springboot.basic.springbootbasic.io.FileOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository, FileInput, FileOutput {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String filename = "voucherList.dat";
    private static Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

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
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        fileOutput(filename, voucher);
        return voucher;
    }

    @Override
    public void fileInput(String fileName) {
        try (
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
             vouchers = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();
        } catch (EOFException eofException) {

        } catch (ClassNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e2) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        }
    }

    @Override
    public void fileOutput(String fileName, Voucher voucher) {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(vouchers);
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        }
    }
}
