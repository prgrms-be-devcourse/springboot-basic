package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;

@Profile({"local", "default"})
@Repository
public class FileVoucherManager implements VoucherManager {

    private static final String FILE_PATH = "src/main/resources/vouchers.csv";

    private final File vouchersCsv;

    public FileVoucherManager() {
        this.vouchersCsv = createFile();
    }

    @Override
    public void save(Voucher voucher) {
        write(voucher, vouchersCsv);
    }

    private static File createFile() {
        File file = new File(FILE_PATH);
        validateFile(file);
        return file;
    }

    private static void validateFile(File file) {
        if (!file.exists()) {
            createFile(file);
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new RuntimeException("Cannot create file. Please check file name or path.", exception);
        }
    }

    private static void write(Voucher voucher, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.append(voucher.getType());
            bufferedWriter.append(", ");
            bufferedWriter.append(voucher.getAmount());
            bufferedWriter.append(System.lineSeparator());
        } catch (IOException exception) {
            throw new RuntimeException("Cannot find file. Please check there is file those name is " + file.getName(), exception);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(vouchersCsv))) {
            return bufferedReader.lines()
                    .map(line -> line.split(", "))
                    .map(t -> Voucher.newInstance(VoucherType.of(t[0]), new VoucherAmount(t[1])))
                    .toList();
        } catch (IOException exception) {
            throw new RuntimeException("Cannot find file. Please check there is file those name is " + vouchersCsv.getName(), exception);
        }
    }
}
