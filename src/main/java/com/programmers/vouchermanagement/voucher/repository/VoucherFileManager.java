package com.programmers.vouchermanagement.voucher.repository;

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
import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.vouchermanagement.util.Message.FILE_EXCEPTION;
import static com.programmers.vouchermanagement.util.Message.IO_EXCEPTION;

@Component
@Profile("file")
public class VoucherFileManager {
    static final String VOUCHER_TYPE_KEY = "voucher_type";
    static final String CREATED_AT_KEY = "created_at";
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileManager.class);
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    public final Map<UUID, Voucher> vouchers;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;

    public VoucherFileManager(AppProperties appProperties) {
        this.filePath = appProperties.resources().path() + appProperties.domains().get("voucher").fileName();
        vouchers = new HashMap<>();
        loadFile();
    }

    public void loadFile() {
        try {
            File file = new File(filePath);
            Map[] voucherObjects = objectMapper.readValue(file, Map[].class);
            loadVouchers(voucherObjects);
        } catch (IOException e) {
            logger.error(IO_EXCEPTION);
            throw new UncheckedIOException(e);
        }
    }

    public void loadVouchers(Map[] voucherObjects) {
        Arrays.stream(voucherObjects).forEach(voucherObject -> {
            Voucher voucher = objectToVoucher(voucherObject);
            vouchers.put(voucher.getId(), voucher);
        });
    }

    private Voucher objectToVoucher(Map voucherObject) {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(VOUCHER_ID_KEY)));
        LocalDateTime createdAt = (LocalDateTime) voucherObject.get(CREATED_AT_KEY);
        String voucherTypeName = String.valueOf(voucherObject.get(VOUCHER_TYPE_KEY));
        long discountValue = Long.parseLong(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        //TODO: check save format
        return new Voucher(voucherId, createdAt, voucherTypeName, discountValue);
    }

    public void saveFile() {
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

    private HashMap<String, Object> voucherToObject(Voucher voucher) {
        HashMap<String, Object> voucherObject = new HashMap<>();
        voucherObject.put(VOUCHER_ID_KEY, voucher.getId().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue());
        voucherObject.put(VOUCHER_TYPE_KEY, voucher.getTypeName());
        voucherObject.put(CREATED_AT_KEY, voucher.getCreatedAt().toString());
        return voucherObject;
    }
}
