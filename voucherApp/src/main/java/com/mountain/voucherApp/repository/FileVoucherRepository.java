package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.properties.FileRepositoryProperties;
import com.mountain.voucherApp.voucher.VoucherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mountain.voucherApp.constants.CommonCharacter.COMMA;
import static com.mountain.voucherApp.constants.CommonCharacter.SLASH;
import static com.mountain.voucherApp.constants.Message.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final FileRepositoryProperties fileRepositoryProperties;
    private final File listFile;
    private static final List<VoucherEntity> storage = new ArrayList<>();

    public FileVoucherRepository(FileRepositoryProperties fileRepositoryProperties) {
        this.fileRepositoryProperties = fileRepositoryProperties;
        this.listFile = new FileSystemResource(getFullPath()).getFile();
    }

    @Override
    public List<VoucherEntity> findAll() {
        init();
        return storage;
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        init();
        try {
            FileWriter fw = new FileWriter(listFile.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            storage.add(voucherEntity);
            bw.write(voucherEntity.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(FILE_INSERT_ERROR);
            throw new RuntimeException(e.getMessage());
        }
        return voucherEntity;
    }

    private void init() {
        if (storage.size() == 0) {
            try {
                BufferedReader inFile = new BufferedReader(new FileReader(getFullPath()));
                String line = null;
                while ((line = inFile.readLine()) != null) {
                    String[] data = line.split(COMMA);
                    VoucherEntity entity = getVoucherEntity(data);
                    storage.add(entity);
                }
                inFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(FILE_READ_ERROR);
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private VoucherEntity getVoucherEntity(String[] data) {
        UUID voucherId = UUID.fromString(data[0]);
        Integer policyId = Integer.valueOf(data[1]);
        Long discountAmount = Long.valueOf(data[2]);
        VoucherEntity entity = new VoucherEntity(voucherId, policyId, discountAmount);
        return entity;
    }

    private String getFullPath() {
        return fileRepositoryProperties.getDir() + SLASH + fileRepositoryProperties.getFileName();
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (!listFile.exists()) {
            listFile.getParentFile().mkdir();
            listFile.createNewFile();
            log.info("{} {}", CREATE_NEW_FILE, listFile.getName());
        }
    }
}
