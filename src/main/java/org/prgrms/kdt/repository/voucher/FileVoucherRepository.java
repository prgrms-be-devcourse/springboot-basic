package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.KdtApplication;
import org.prgrms.kdt.utils.PathProperties;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);


    private static final Map<UUID, Voucher> voucherStorage = new HashMap<>();

    private final String FILE_PATH;

    public FileVoucherRepository(PathProperties pathProperties) {
        this.FILE_PATH = pathProperties.getOrigin();
    }




    @Override
    public Voucher insert(Voucher voucher) {
        try {
            FileOutputStream fileStream = new FileOutputStream(FILE_PATH);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
            voucherStorage.put(voucher.getVoucherId(), voucher);
            objectOutputStream.writeObject(voucherStorage);

            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return voucher;
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherStorage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        ArrayList<Voucher> values = null;
        logger.info(FILE_PATH);
        try {
            FileInputStream fileStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

            Object object = objectInputStream.readObject();

            objectInputStream.close();

            HashMap hashMap = (HashMap) object;
            Iterator<UUID> it = hashMap.keySet().iterator();

            values = new ArrayList<>();

            while (it.hasNext()) {
                UUID key = it.next();
                Voucher value = (Voucher) hashMap.get(key);

                values.add(value);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return values;
        }
    }


}
