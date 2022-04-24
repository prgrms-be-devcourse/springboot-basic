package com.mountain.voucherApp.adapter.out.persistence.voucher;

import com.mountain.voucherApp.application.port.out.VoucherPort;
import com.mountain.voucherApp.shared.properties.FileRepositoryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mountain.voucherApp.shared.constants.CommonCharacter.COMMA;
import static com.mountain.voucherApp.shared.constants.CommonCharacter.SLASH;
import static com.mountain.voucherApp.shared.constants.Message.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherPort {

    private final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final FileRepositoryProperties fileRepositoryProperties;
    private final File voucherListFile;
    private static final List<VoucherEntity> storage = new ArrayList<>();

    public FileVoucherRepository(FileRepositoryProperties fileRepositoryProperties) {
        this.fileRepositoryProperties = fileRepositoryProperties;
        this.voucherListFile = new FileSystemResource(getFullPath()).getFile();
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
            FileWriter fw = new FileWriter(voucherListFile.getAbsoluteFile(), true);
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

    public void remove() {
        if (voucherListFile.exists()) {
            voucherListFile.delete();
        }
    }


    @Override
    public Optional<VoucherEntity> findByPolicyIdAndDiscountAmount(int discountPolicyId, long discountAmount) {
        return storage.stream()
                .filter((e) -> (e.getDiscountPolicyId() == discountPolicyId) && (e.getDiscountAmount() == discountAmount))
                .findFirst();
    }

    @Override
    public Optional<VoucherEntity> findById(UUID voucherId) {
        return Optional.empty();
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (!voucherListFile.exists()) {
            voucherListFile.getParentFile().mkdir();
            voucherListFile.createNewFile();
            log.info("{} {}", CREATE_NEW_FILE, voucherListFile.getName());
        }
    }
}
