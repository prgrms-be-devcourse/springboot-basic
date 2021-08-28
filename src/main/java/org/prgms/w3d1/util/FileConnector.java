package org.prgms.w3d1.util;

import org.prgms.w3d1.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface FileConnector<T> {

    Logger logger = LoggerFactory.getLogger(FileConnector.class);

    default Map<UUID, T> fileConnect(String path) {
        Map<UUID, T> storage;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            storage = (ConcurrentHashMap<UUID, T>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            storage = new ConcurrentHashMap<>();
        }
        return storage;
    }

    default void fileInsert(String path, Map<UUID, T> storage) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(storage);
            fos.close();
            oos.close();
        } catch (IOException e) {
            logger.error(MessageFormat.format("Error on FileConnector : {0}", e.getClass().getCanonicalName()));
        }
    }
}
