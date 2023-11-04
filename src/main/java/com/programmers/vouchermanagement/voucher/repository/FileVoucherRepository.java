package com.programmers.vouchermanagement.voucher.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.profiles.FileEnabledCondition;
import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.util.JSONFileManager;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@Repository
@Conditional(FileEnabledCondition.class)
public class FileVoucherRepository implements VoucherRepository {
    //constants
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String VOUCHER_CREATED_AT_KEY = "created_at";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    private static final String VOUCHER_TYPE_KEY = "voucher_type";
    private static final String CUSTOMER_ID_KEY = "customer_id";

    private final String filePath;
    private final JSONFileManager<UUID, Voucher> jsonFileManager;
    private final Map<UUID, Voucher> vouchers;

    private final Function<Map, Voucher> objectToVoucher = (voucherObject) -> {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(VOUCHER_ID_KEY)));
        LocalDateTime createdAt = LocalDateTime.parse(String.valueOf(voucherObject.get(VOUCHER_CREATED_AT_KEY)));
        BigDecimal discountValue = new BigDecimal(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        String voucherTypeName = String.valueOf(voucherObject.get(VOUCHER_TYPE_KEY));
        VoucherType voucherType = VoucherType.findVoucherTypeByName(voucherTypeName);
        String customerIdString = String.valueOf(voucherObject.get(CUSTOMER_ID_KEY));
        UUID customerId = customerIdString.equals("null") ? null : UUID.fromString(customerIdString);
        return new Voucher(voucherId, createdAt, discountValue, voucherType, customerId);
    };
    private final Function<Voucher, HashMap<String, Object>> voucherToObject = (voucher) -> {
        HashMap<String, Object> voucherObject = new HashMap<>();
        voucherObject.put(VOUCHER_ID_KEY, voucher.getVoucherId().toString());
        voucherObject.put(VOUCHER_CREATED_AT_KEY, voucher.getCreatedAt().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue().toString());
        voucherObject.put(VOUCHER_TYPE_KEY, voucher.getVoucherType().name());
        voucherObject.put(CUSTOMER_ID_KEY, voucher.getCustomerId());
        return voucherObject;
    };

    public FileVoucherRepository(FileProperties fileProperties, @Qualifier("voucher") JSONFileManager<UUID, Voucher> jsonFileManager) {
        this.filePath = fileProperties.getVoucherFilePath();
        this.jsonFileManager = jsonFileManager;
        this.vouchers = new HashMap<>();
        loadVouchersFromJSON();
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
    public List<Voucher> findByType(VoucherType voucherType) {
        return vouchers.values()
                .stream()
                .filter(voucher -> voucher.isSameType(voucherType))
                .toList();
    }

    @Override
    public List<Voucher> findByCreatedAt(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return vouchers.values()
                .stream()
                .filter(voucher -> voucher.isCreatedInBetween(startDateTime, endDateTime))
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return vouchers.values()
                .stream()
                .filter(voucher -> Objects.equals(voucher.getCustomerId(), customerId))
                .toList();
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
    }

    @Override
    @Profile("test")
    public void deleteAll() {
        vouchers.clear();
    }

    private void loadVouchersFromJSON() {
        List<Voucher> loadedVouchers = jsonFileManager.loadFile(filePath, objectToVoucher);
        loadedVouchers.forEach(voucher -> vouchers.put(voucher.getVoucherId(), voucher));
    }

    private void saveFile() {
        jsonFileManager.saveFile(filePath, vouchers, voucherToObject);
    }
}
