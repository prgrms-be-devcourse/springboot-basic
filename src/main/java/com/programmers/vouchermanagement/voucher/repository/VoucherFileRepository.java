package com.programmers.vouchermanagement.voucher.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.properties.AppProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

import static com.programmers.vouchermanagement.constant.Message.*;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    //constants
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    private static final String VOUCHER_TYPE_KEY = "voucher_type";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;
    private final Map<UUID, Voucher> vouchers;

    public VoucherFileRepository(AppProperties appProperties) {
        this.filePath = appProperties.resources().path() + appProperties.domains().get("voucher").fileName();
        this.vouchers = new HashMap<>();
        loadFile();
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.voucherId(), voucher);
        saveFile();
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public void delete(UUID id) {
        Optional.ofNullable(vouchers.remove(id)).orElseThrow(() -> new RuntimeException(NOT_DELETED));
        saveFile();
    }

    @Override
    public void deleteAll() {
        if (!vouchers.isEmpty())
            vouchers.clear();
        saveFile();
    }

    @Override
    public void update(Voucher voucher) {
        Optional.ofNullable(vouchers.get(voucher.voucherId())).orElseThrow(() -> new RuntimeException(NOT_UPDATED));
        vouchers.put(voucher.voucherId(), voucher);
        saveFile();
    }

    private void loadFile() {
        try {
            File file = new File(filePath);
            Map[] voucherObjects = objectMapper.readValue(file, Map[].class);
            loadVouchers(voucherObjects);
        } catch (IOException e) {
            logger.error(IO_EXCEPTION);
            throw new UncheckedIOException(e);
        }
    }

    private void loadVouchers(Map[] voucherObjects) {
        Arrays.stream(voucherObjects).forEach(voucherObject -> {
            Voucher voucher = objectToVoucher(voucherObject);
            vouchers.put(voucher.voucherId(), voucher);
        });
    }

    private Voucher objectToVoucher(Map voucherObject) {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(VOUCHER_ID_KEY)));
        long discountValue = Long.parseLong(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        String voucherTypeName = String.valueOf(voucherObject.get(VOUCHER_TYPE_KEY));
        VoucherType voucherType = VoucherType.findCreateMenu(voucherTypeName)
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE);
                    return new NoSuchElementException(INVALID_VOUCHER_TYPE);
                });
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
        voucherObject.put(VOUCHER_ID_KEY, voucher.voucherId().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.discountValue());
        voucherObject.put(VOUCHER_TYPE_KEY, voucher.voucherType().name());
        return voucherObject;
    }
}
