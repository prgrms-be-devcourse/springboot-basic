package com.programmers.vouchermanagement.voucher.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.properties.AppProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    private static final String VOUCHER_TYPE_KEY = "voucher_type";
    private static final String FILE_EXCEPTION = "Error raised while opening the file.";
    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "Voucher type should be either fixed amount or percent discount voucher.";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath;
    private final Map<UUID, Voucher> vouchers;

    public FileVoucherRepository(AppProperties appProperties) {
        this.filePath = appProperties.getResources().getPath() + appProperties.getDomains().get("voucher.file-name");
        this.vouchers = new HashMap<>();
        loadFile();
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        saveFile();
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    private void loadFile() {
        try {
            File file = new File(filePath);
            Map[] voucherObjects = objectMapper.readValue(file, Map[].class);
            loadVouchers(voucherObjects);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        VoucherType voucherType = VoucherType.findCreateMenu(voucherTypeName)
                .orElseThrow(() -> new NoSuchElementException(INVALID_VOUCHER_TYPE_MESSAGE));
        return new Voucher(voucherId, discountValue, voucherType);
    }

    public void saveFile() {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            List<HashMap<String, Object>> voucherObjects = new ArrayList<>();
            if (!vouchers.isEmpty()) {
                vouchers.values().forEach(voucher -> {
                    HashMap<String, Object> voucherObject = voucherToObject(VoucherResponse.from(voucher));
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

    private HashMap<String, Object> voucherToObject(VoucherResponse voucherResponse) {
        HashMap<String, Object> voucherObject = new HashMap<>();
        voucherObject.put(VOUCHER_ID_KEY, voucherResponse.voucherId().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucherResponse.discountValue().toString());
        voucherObject.put(VOUCHER_TYPE_KEY, voucherResponse.voucherType().name());
        return voucherObject;
    }
}
