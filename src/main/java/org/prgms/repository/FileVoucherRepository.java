package org.prgms.repository;

import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository{
    @Override
    public void save(Voucher voucher) {
        String filename = String.format("./objects/%s.obj", voucher.hashCode());
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(voucher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Voucher> findAll() {
        File folder = new File("./objects");
        File[] list = folder.listFiles();
        String pattern = "*.obj";
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        List<Voucher> vouchers = new ArrayList<>();
        for (File filename : Objects.requireNonNull(list)) {
            if (matcher.matches(filename.toPath().getFileName())) {
                try (FileInputStream fis = new FileInputStream(filename);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Object obj = ois.readObject();
                    vouchers.add((Voucher) obj);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return vouchers;
    }
}
