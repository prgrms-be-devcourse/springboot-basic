package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(LocalFileVoucherRepository.class);

    AtomicLong sequencer = new AtomicLong(1);
    Map<Long, Voucher> db = new HashMap<>();

    public LocalFileVoucherRepository(
            @Value("${voucher.file.voucher.location}") String directory,
            @Value("${voucher.file.voucher.filename}") String filename
    ) {
        log.info("Using directory {} as local voucher directory location.", directory);
        log.info("Using filename {} as local voucher file.", filename);

        Path fileDirectory = Paths.get(directory);
        if(!Files.exists(fileDirectory)) {
            try {
                Files.createDirectory(fileDirectory);
                log.debug("Created local voucher directory at {}", fileDirectory.toString());
            } catch (IOException ex) {
                log.error("Failed to create local voucher directory at {}", fileDirectory.toString());
                System.exit(1);
            }
        }

        this.file = fileDirectory.resolve(filename);
        if(!Files.exists(file)) {
            try {
                Files.createFile(file);
                log.debug("Created local voucher file at {}", file.toString());
            } catch (IOException ex) {
                log.error("Failed to create local voucher file at {}", file.toString());
                System.exit(1);
            }
        }
    }

    @Override
    public void persistVouchers() {
        try {
            OutputStream writer = Files.newOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(writer);
            objectOutputStream.writeObject(new ArrayList<>(db.values()));
            log.debug("Wrote voucher objects to file {}", file.toString());
            objectOutputStream.flush();
            objectOutputStream.close();
            log.debug("Persisted vouchers to file {}", file.toString());
        } catch (IOException ex) {
            log.error("IOException occur on persisting local voucher file at {} - {}", file.toString(), ex.getLocalizedMessage());
            System.exit(1);
        }
    }

    @Override
    public void loadVouchers() {
        try {
            InputStream reader = Files.newInputStream(file);// https://www.baeldung.com/reading-file-in-java
            log.debug("Opened InputStream from file {}", file.toString());
            if (reader.available() < 1) return;

            ObjectInputStream objectInputStream = new ObjectInputStream(reader);
            List<Voucher> vouchers = (List<Voucher>) objectInputStream.readObject();
            log.debug("Read voucher objects from bytes");
            vouchers.forEach(voucher -> db.put(voucher.getId(), voucher));
            objectInputStream.close();
            log.debug("Read vouchers from file {}", file.toString());

            Optional<Long> max = vouchers.stream().map(Voucher::getId).max(Long::compareTo);
            long maxNum = max.isPresent() ? max.get() : 0;
            sequencer = new AtomicLong(maxNum + 1);
            log.debug("Sequencer initialized as {}", maxNum + 1);
        } catch (IOException ex) {
            log.error("IOException occur on initializing vouchers from local file {}", file.toString());
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            log.error("ClassNotFoundException occur on initializing vouchers from local file {}", file.toString());
            System.exit(1);
        }

        log.info("Read vouchers from file {} completed.", file.toString());
    }

    @Override
    public Voucher save(Voucher voucher) {
        long id = sequencer.getAndAdd(1);
        voucher.setId(id);
        db.put(id, voucher);
        log.debug("Saved voucher '{}' to database.", voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return new ArrayList<>(db.values());
    }
}
