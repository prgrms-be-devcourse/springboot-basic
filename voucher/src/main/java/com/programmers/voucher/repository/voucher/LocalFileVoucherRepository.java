package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class LocalFileVoucherRepository implements VoucherRepository {

    private final Path file;

    AtomicLong sequencer = new AtomicLong(1);
    Map<Long, Voucher> db = new HashMap<>();

    public LocalFileVoucherRepository() throws IOException {
        String filename = "vouchers.db";

        Path fileDirectory = Paths.get("storage");
        if(!Files.exists(fileDirectory)) {
            Files.createDirectory(fileDirectory);
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            Files.createFile(file);
        }

        loadVouchers();
    }

    @Override
    public void saveVouchers() throws IOException {
        OutputStream writer = Files.newOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writer);
        db.values().forEach(voucher -> {
            try {
                objectOutputStream.writeObject(voucher);
            } catch (IOException e) {
                System.err.printf("Could not save voucher [ id: %d, name: %s, type: %s ]%n",
                        voucher.getId(), voucher.getName(), voucher.getType().name());
            }
        });
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public void loadVouchers() throws IOException {
        InputStream reader = Files.newInputStream(file);// https://www.baeldung.com/reading-file-in-java
        if(reader.available() < 1) return;

        ObjectInputStream objectInputStream = new ObjectInputStream(reader);
        long maxNum = -1;
        while(objectInputStream.available() > 0) {
            Voucher voucher;
            try {
                voucher = (Voucher) objectInputStream.readObject();
            } catch (ClassNotFoundException ex) {
                voucher = new Voucher(-1, "N/A", Voucher.type.NA);
            }
            db.put(voucher.getId(), voucher);
            maxNum = Math.max(maxNum, voucher.getId());
        }
        objectInputStream.close();

        sequencer = new AtomicLong(maxNum+1);
    }

    @Override
    public Voucher save(String name, Voucher.type type) {
        long id = sequencer.getAndAdd(1);
        Voucher voucher = new Voucher(id, name, type);
        db.put(id, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return new ArrayList<>(db.values());
    }
}
