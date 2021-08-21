package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LocalFileVoucherRepository implements VoucherRepository {

    private final Path file;

    AtomicLong sequencer = new AtomicLong(1);
    Map<Long, Voucher> db = new HashMap<>();

    public LocalFileVoucherRepository(
            @Value("${voucher.file.voucher.location}") String directory,
            @Value("${voucher.file.voucher.filename}") String filename
    ) throws IOException {
        Path fileDirectory = Paths.get(directory);
        if(!Files.exists(fileDirectory)) {
            Files.createDirectory(fileDirectory);
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    @Override
    public void persistVouchers() {
        try {
            OutputStream writer = Files.newOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(writer);
            objectOutputStream.writeObject(new ArrayList<>(db.values()));
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException ex) {
            System.err.println("IOException occur on persisting local file voucher repository.");
            System.exit(1);
        }
    }

    @Override
    public void loadVouchers() {
        try {
            InputStream reader = Files.newInputStream(file);// https://www.baeldung.com/reading-file-in-java
            if (reader.available() < 1) return;

            ObjectInputStream objectInputStream = new ObjectInputStream(reader);
            List<Voucher> vouchers = (List<Voucher>) objectInputStream.readObject();
            vouchers.forEach(voucher -> db.put(voucher.getId(), voucher));
            objectInputStream.close();

            Optional<Long> max = vouchers.stream().map(Voucher::getId).max(Long::compareTo);
            long maxNum = max.isPresent() ? max.get() : 0;
            sequencer = new AtomicLong(maxNum + 1);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("IOException occur on initializing local file voucher repository.");
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException occur on initializing local file voucher repository.");
            System.exit(1);
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        long id = sequencer.getAndAdd(1);
        voucher.setId(id);
        db.put(id, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return new ArrayList<>(db.values());
    }
}
