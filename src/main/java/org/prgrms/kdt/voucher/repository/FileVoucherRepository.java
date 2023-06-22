package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Qualifier("file")
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> voucherStorage = new HashMap<>();

    @Value(value = "${kdt.file-path}")
    private String FILE_PATH;
    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(FILE_PATH);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
        voucherStorage.put(voucher.getVoucherId(), voucher);
        objectOutputStream.writeObject(voucherStorage);

        objectOutputStream.close();
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherStorage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() throws IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream(FILE_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

        Object object = objectInputStream.readObject();

        objectInputStream.close();

        HashMap hashMap = (HashMap) object;
        Iterator<String> it = hashMap.keySet().iterator();

        ArrayList<Voucher> values = new ArrayList<>();

        while (it.hasNext()) {
            String key = it.next();
            Voucher value = (Voucher) hashMap.get(key);

            values.add(value);
        }

        return values;
    }
}
