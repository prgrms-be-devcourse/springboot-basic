package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("prod")
@Repository
public class FileVoucherRepositoy implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoy.class);

    private static Map<UUID, Voucher> storage;

    private final String PATH = "src/main/resources/voucher.txt";

    // Postconsruct 할 때 열어서 메모리로 쌓고 preDestroy가 일어날 때 닫고
    // File과 저장소를 연결하는 provider 클래스를 만들어보자
    // findbyId 구현

    @PostConstruct
    private void postConstruct() {
        fileConnect(PATH);
    }

    @PreDestroy
    private void preDestory(){
        fileInsert(PATH);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    private void fileConnect(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            storage = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            storage = new ConcurrentHashMap<>();
            logger.error(MessageFormat.format("Error on FileVoucherRepositoy : {0}", e.getClass().getCanonicalName()));
        }
    }

    private void fileInsert(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(storage);
            fos.close();
            oos.close();
        } catch (IOException e) {
            logger.error(MessageFormat.format("Error on FileVoucherRepositoy : {0}", e.getClass().getCanonicalName()));
        }
    }

}
