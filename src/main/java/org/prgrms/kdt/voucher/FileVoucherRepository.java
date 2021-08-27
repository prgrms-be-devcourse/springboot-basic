package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    @Value("${kdt.voucherFilePath}")
    private String voucherFilePath;
    private Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getID(), voucher);
        return voucher;
    }

    @Override
    public int numVouchers() {
        return voucherMap.size();
    }

    @Override
    public List<Voucher> getList() {
        return voucherMap.values().stream().toList();
    }

    @Override
    public void afterPropertiesSet() {
        try(
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(voucherFilePath));
        ) {
            Voucher read;
            while ((read = (Voucher) objectInputStream.readObject()) != null) {
                voucherMap.put(read.getID(), read);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try (
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(voucherFilePath));
        ) {
            voucherMap.entrySet().stream().map((o) -> o.getValue()).forEach(s -> {
                System.out.println(s);
                try {
                    objectOutputStream.writeObject(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

