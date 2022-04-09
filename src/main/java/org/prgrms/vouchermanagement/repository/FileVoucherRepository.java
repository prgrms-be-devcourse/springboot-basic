package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    @Value("${file-path.voucher}")
    private String voucherFilePath;
    private Map<UUID, Voucher> mapStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return Optional.ofNullable(mapStorage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        System.out.println("-------------local FileVoucherRepository------------------");
        return mapStorage.entrySet()
                .stream()
                .map(o -> o.getValue()).toList();
    }

    @Override
    public Voucher save(Voucher voucher) {

        mapStorage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @PostConstruct
    public void postConstruct() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(voucherFilePath))) {

            Voucher readVoucher;
            while ((readVoucher = (Voucher) objectInputStream.readObject()) != null) {
                System.out.println(readVoucher);
                mapStorage.put(readVoucher.getVoucherId(), readVoucher);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("FileVoucherRepository postConstruct() 실행");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void preDestory() {
        System.out.println("FileVoucherRepository preDestory() 실행");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(voucherFilePath))) {

            mapStorage.entrySet()
                    .stream()
                    .map(o -> o.getValue())
                    .forEach(s -> {
                        System.out.println(s);
                        try {
                            objectOutputStream.writeObject(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
