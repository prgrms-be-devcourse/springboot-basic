package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final ResourceLoader resourceLoader;

    private static final String SUCCESSFULLY_WRITE_OBJECT_TO_FILE = "INFO : Successfully write object to file.";
    private static final String READING_ERROR = "I/O ERROR : Error occured during reading voucher data.";
    private static final String WRITING_ERROR = "I/O ERROR : Error occured during writing voucher to file.";
    private static final String CLASS_NOT_FOUND_ERROR = "RUNTIME ERROR : reading data From File is Not A Type of Voucher.";

    @Value("${file.path.voucher}")
    private String filePath;

    public FileVoucherRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Voucher> findAll() {
        return readAllDataFromFile();
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        return writeDataToFile(voucher);
    }

    private Optional<Voucher> writeDataToFile(Voucher voucher) {
        Resource resource = resourceLoader.getResource(filePath);
        try {
            FileOutputStream fileOut = new FileOutputStream(resource.getFile(), true);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(voucher);
        } catch (IOException e) {
            log.error(WRITING_ERROR);
            return Optional.empty();
        }
        log.info(SUCCESSFULLY_WRITE_OBJECT_TO_FILE);
        return Optional.of(voucher);
    }

    private List<Voucher> readAllDataFromFile() {
        List<Voucher> voucherList = new ArrayList<>();
        Resource resource = resourceLoader.getResource(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(resource.getFile());
            while (Objects.requireNonNull(fileInputStream).available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream); // 스트림 헤더 객체마다 갱신
                Voucher voucher = (Voucher) objectInputStream.readObject();
                voucherList.add(voucher);
            }
        } catch (IOException e) {
            log.error(READING_ERROR);
            return List.of();
        } catch (ClassNotFoundException e) {
            log.error(CLASS_NOT_FOUND_ERROR);
            return List.of();
        }
        return voucherList;
    }

    @Override
    public void clear() {

    }
}
