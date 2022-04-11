package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class FileVoucherRepository implements
        VoucherRepository, InitializingBean, DisposableBean {

    private final String filePath = "C:\\Users\\chlwl\\Desktop\\springboot-basic\\src\\main\\resources\\storage\\record.ser";

    private List<Voucher> list;

    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private ObjectOutputStream objectOutputStream;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private ObjectInputStream objectInputStream;


    public FileVoucherRepository() {
        try {
            file = new File(filePath);
        }
        catch (NullPointerException e) {
            System.out.println("로컬 저장소를 불러올 수 없습니다.");
        }
    }

    @Override
    public void afterPropertiesSet() {
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            objectInputStream = new ObjectInputStream(bufferedInputStream);

            list = (ArrayList)objectInputStream.readObject();

            objectInputStream.close();
            bufferedInputStream.close();
            fileInputStream.close();
        } catch (EOFException e) {
            list = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        list.add(voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return list.stream()
                .filter(voucher -> voucher.getVoucherId().toString().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        return list;
    }

    @Override
    public void destroy() {
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

            objectOutputStream.writeObject(list);

            objectOutputStream.close();
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
