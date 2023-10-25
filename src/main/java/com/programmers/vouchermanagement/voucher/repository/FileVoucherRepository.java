package com.programmers.vouchermanagement.voucher.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.programmers.vouchermanagement.configuration.properties.AppProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@Repository
@Profile({"prod", "test"})
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    //constants
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    private static final String VOUCHER_TYPE_KEY = "voucher_type";
    private static final String FILE_EXCEPTION = "Error raised while opening the file.";

    //messages
    private static final String IO_EXCEPTION_LOG_MESSAGE = "Error raised while reading vouchers";
    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "Voucher type should be either fixed amount or percent discount voucher.";
    private static final String NO_VOUCHER_STORED = "No Voucher is stored yet!";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;
    private final Map<UUID, Voucher> vouchers;

    public FileVoucherRepository(AppProperties appProperties) {
        this.filePath = appProperties.getVoucherFilePath();
        this.vouchers = new HashMap<>();
        loadFile();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        saveFile();
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .toList();
    }

    @Override
    @Profile("test")
    public void deleteAll() {
        vouchers.clear();
    }

    private void loadFile() {
        try {
            File file = new File(filePath);
            Map[] voucherObjects = objectMapper.readValue(file, Map[].class);
            loadVouchers(voucherObjects);
        } catch (MismatchedInputException e) {
            logger.debug(NO_VOUCHER_STORED);
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }
    }

    private void loadVouchers(Map[] voucherObjects) {
        Arrays.stream(voucherObjects).forEach(voucherObject -> {
            Voucher voucher = objectToVoucher(voucherObject);
            vouchers.put(voucher.getVoucherId(), voucher);
        });
    }

    private Voucher objectToVoucher(Map voucherObject) {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(VOUCHER_ID_KEY)));
        BigDecimal discountValue = new BigDecimal(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        String voucherTypeName = String.valueOf(voucherObject.get(VOUCHER_TYPE_KEY));
        VoucherType voucherType = VoucherType.findVoucherType(voucherTypeName);
        return new Voucher(voucherId, discountValue, voucherType);
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
        voucherObject.put(VOUCHER_ID_KEY, voucher.getVoucherId().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue().toString());
        voucherObject.put(VOUCHER_TYPE_KEY, voucher.getVoucherType().name());
        return voucherObject;
    }
}
