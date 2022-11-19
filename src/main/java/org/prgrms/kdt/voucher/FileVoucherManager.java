package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Profile("local")
@Repository
public class FileVoucherManager implements VoucherManager {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManager.class);

    private static final String DELIMITER = ", ";
    private final String filePath;

    public FileVoucherManager(@Value("${voucher.file-path}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Voucher save(Voucher voucher) {
        File vouchersCsv = loadFile();

        Voucher savedVoucher = Voucher.from(getNextLineNumber(vouchersCsv), voucher.getType(), voucher.getAmount());
        write(savedVoucher, vouchersCsv);
        return savedVoucher;
    }

    private File loadFile() {
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                logger.info("file created. [FILE PATH]: " + filePath);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + file.getName() + ".[File Path]: " + filePath, exception);
        }
        return file;
    }

    private long getNextLineNumber(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .count() + 1;
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + file.getName(), exception);
        }
    }

    private void write(Voucher voucher, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.append(String.valueOf(voucher.getId()));
            bufferedWriter.append(DELIMITER);
            bufferedWriter.append(voucher.getType().getType().toUpperCase());
            bufferedWriter.append(DELIMITER);
            bufferedWriter.append(String.valueOf(voucher.getAmount().getValue()));
            bufferedWriter.append(System.lineSeparator());
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + file.getName() + ".[File Path]: " + filePath, exception);
        }
    }

    @Override
    public List<Voucher> findAll() {
        File vouchersCsv = loadFile();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(vouchersCsv))) {
            return bufferedReader.lines()
                    .map(FileVoucherManager::mapToVoucher)
                    .toList();
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + vouchersCsv.getName(), exception);
        }
    }

    @Override
    public Optional<Voucher> findById(long id) {
        File vouchersCsv = loadFile();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(vouchersCsv))) {
            return bufferedReader.lines()
                    .map(FileVoucherManager::mapToVoucher)
                    .filter(voucher -> voucher.getId() == id)
                    .findFirst();
        } catch (IOException exception) {
            throw new IllegalArgumentException("Cannot find file. Please check there is file those name is " + vouchersCsv.getName(), exception);
        }
    }

    @Override
    public void deleteAll() {
        try {
            new FileOutputStream(filePath).close();
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Override
    public void update(Voucher voucher) {
        throw new UnsupportedOperationException("Unsupported command.");
    }

    @Override
    public void deleteById(long voucherId) {
        throw new UnsupportedOperationException("Unsupported command.");
    }

    private static Voucher mapToVoucher(String line) {
        try {
            String[] tokens = line.split(DELIMITER);
            return Voucher.from(Long.parseLong(tokens[0]), VoucherType.of(tokens[1]), new VoucherAmount(tokens[2]));
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new RuntimeException("Invalid File. Please write the file in following format. [Format]: Id, Type, Amount", exception);
        }
    }
}
