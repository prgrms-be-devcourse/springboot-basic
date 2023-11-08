package com.programmers.vouchermanagement.voucher.repository.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.properties.AppProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

import static com.programmers.vouchermanagement.util.Message.FILE_EXCEPTION;
import static com.programmers.vouchermanagement.util.Message.IO_EXCEPTION;
import static com.programmers.vouchermanagement.voucher.repository.util.VoucherDomainMapper.objectToVoucher;
import static com.programmers.vouchermanagement.voucher.repository.util.VoucherDomainMapper.voucherToObject;

@Component
@Profile("file")
public class VoucherFileManager {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileManager.class);
    private final ObjectMapper objectMapper;
    private final String filePath;

    public VoucherFileManager(AppProperties appProperties, ObjectMapper objectMapper) {
        this.filePath = appProperties.resources().path() +
                appProperties.domains().get("voucher").fileName();
        this.objectMapper = objectMapper;
    }

    public Map<UUID, Voucher> loadFile() {
        try {
            File file = new File(filePath);
            Map<String, String>[] voucherObjects = objectMapper.readValue(file, Map[].class);
            return loadVouchers(voucherObjects);
        } catch (IOException e) {
            logger.error(IO_EXCEPTION);
            throw new UncheckedIOException(e);
        }
    }

    private Map<UUID, Voucher> loadVouchers(Map<String, String>[] voucherObjects) {
        Map<UUID, Voucher> vouchers = new HashMap<>();
        Arrays.stream(voucherObjects).forEach(voucherObject -> {
            Voucher voucher = objectToVoucher(voucherObject);
            vouchers.put(voucher.getId(), voucher);
        });
        return vouchers;
    }

    public void saveFile(Map<UUID, Voucher> vouchers) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            List<HashMap<String, Object>> voucherObjects = new ArrayList<>();
            if (!vouchers.isEmpty()) {
                vouchers.values().forEach(voucher -> {
                    HashMap<String, Object> voucherObject = voucherToObject(voucher);
                    voucherObjects.add(voucherObject);
                });
            }
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(voucherObjects);
            fileWriter.write(jsonStr);
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(FILE_EXCEPTION);
        }
    }
}
