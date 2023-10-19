package org.programmers.springboot.basic.domain.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.AppConstants;
import org.programmers.springboot.basic.config.AppConfig;
import org.programmers.springboot.basic.domain.voucher.dto.CsvVoucherDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.util.exception.CSVFileIOFailureException;
import org.programmers.springboot.basic.util.manager.CSVFileManager;
import org.programmers.springboot.basic.util.properties.FileProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Profile("default")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private final File FILE;
    private final CSVFileManager csvFileManager;
    private final VoucherMapper voucherMapper;
    private final FileProperties fileProperties;
    private final ReentrantLock lock = new ReentrantLock();

    public FileVoucherRepository(CSVFileManager csvFileManager, VoucherMapper voucherMapper, FileProperties fileProperties) {
        this.csvFileManager = csvFileManager;
        this.voucherMapper = voucherMapper;
        this.fileProperties = fileProperties;
        String filePath = getFilePath();
        FILE = new File(filePath);
    }

    @Override
    public void save(Voucher voucher) {
        lock.lock();
        try {
            write(voucher);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Voucher> findAll() {
        lock.lock();
        try {
            return read();
        } finally {
            lock.unlock();
        }
    }

    private List<Voucher> read() {

        List<Voucher> voucherList = new ArrayList<>();
        String line;

        try {
            BufferedReader reader = this.csvFileManager.getBufferedReader(FILE);

            while ((line = reader.readLine()) != null) {
                String[] token = line.split(AppConstants.CSV_SEPARATOR);
                //if (token.length < 3) return voucherList;
                CsvVoucherDto voucherDto = voucherMapper.deserialize(token);
                Voucher voucher = voucherMapper.mapToVoucher(voucherDto);
                voucherList.add(voucher);
            }

        } catch (FileNotFoundException e) {
            throw new CSVFileIOFailureException("Exception Occurred: No matching file found!");
        } catch (IOException e) {
            throw new CSVFileIOFailureException("Exception Occurred: Failed to read file!");
        }

        return voucherList;
    }


    private void write(Voucher voucher) {

        try {
            BufferedWriter writer = this.csvFileManager.getBufferedWriter(FILE);

            String serializer = this.voucherMapper.serialize(voucher);
            writer.write(serializer);
            writer.newLine();
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new CSVFileIOFailureException("Exception Occurred: No matching file found!");
        } catch (IOException e) {
            throw new CSVFileIOFailureException("Exception Occurred: Failed to write file!");
        }
    }

    private String getFilePath() {

        String folderPath = this.fileProperties.getUserDir();
        String fileName = this.fileProperties.getNames().get("voucher").getFileName();
        String resourcePath = this.fileProperties.getResources().getPath();
        String filePath = folderPath + resourcePath + fileName;

        if (AppConfig.isRunningFromJar()) {
            folderPath = this.fileProperties.getProjDir();
            resourcePath = this.fileProperties.getResources().getJar();
            filePath =  folderPath + resourcePath + fileName;
        }

        return filePath;
    }
}
