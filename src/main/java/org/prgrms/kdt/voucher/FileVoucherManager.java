package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"local", "default"})
@Repository
public class FileVoucherManager implements VoucherManager {

    private static Logger logger = LoggerFactory.getLogger(FileVoucherManager.class);

    private static final String FILE_PATH = "src/main/resources/vouchers.txt";

    private final File file;

    public FileVoucherManager() {
        this.file = createFile();
    }

    @Override
    public void save(Voucher voucher) {
        write(voucher, file);
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
        } catch (IOException e) {
            logger.error("Cannot create file. Please check file name or path.");
            throw new RuntimeException("File Create Error");
        }
    }

    private static void write(Voucher voucher, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.append(voucher.getType());
            bufferedWriter.append(" ");
            bufferedWriter.append(voucher.getAmount());
            bufferedWriter.append(System.lineSeparator());
        } catch (IOException exception) {
            logger.error("Cannot find file. Please check there is file those name is {}", file.getName());
            throw new RuntimeException("File Write Error");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .map(line -> line.split(" "))
                    .map(t -> Voucher.newInstance(VoucherType.of(t[0]), new VoucherAmount(t[1])))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
