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
import java.util.List;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository, FileInput, FileOutput {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final String filename = "voucherList.dat";
    private final List<Voucher> voucherList = new ArrayList<>();

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = null;
        try {
            voucherList = (List<Voucher>) fileInput(filename);
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
        }

        return voucherList;
    }

    @Override
    public void save(Voucher voucher) {
        try {
            fileOutput(voucher, filename);
        } catch (IOException e) {
            logger.error(filename + " 해당 파일을 찾을 수 없습니다.");
            e.printStackTrace();
        }
    }

    @Override
    public Object fileInput(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        List<Voucher> voucherList = null;
        try {
            voucherList = (List<Voucher>) ois.readObject();
        } catch (ClassNotFoundException e) {
            logger.error(e.toString());
        }

        ois.close();
        fis.close();

        return voucherList;
    }

    @Override
    public void fileOutput(Object voucher, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        voucherList.add((Voucher) voucher);

        oos.writeObject(voucherList);
        oos.flush();

        oos.close();
        fos.close();
    }
}
