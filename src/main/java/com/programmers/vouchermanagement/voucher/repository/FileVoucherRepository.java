package com.programmers.vouchermanagement.voucher.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.AppProperties;
import com.programmers.vouchermanagement.util.JSONFileManager;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@Repository
@Profile({"file", "test"})
public class FileVoucherRepository implements VoucherRepository {
    //constants
    private static final String VOUCHER_ID_KEY = "voucher_id";
    private static final String DISCOUNT_VALUE_KEY = "discount_value";
    private static final String VOUCHER_TYPE_KEY = "voucher_type";

    private final String filePath;
    private final JSONFileManager<UUID, Voucher> jsonFileManager;
    private final Map<UUID, Voucher> vouchers;

    private final Function<Map, Voucher> objectToVoucher = (voucherObject) -> {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(VOUCHER_ID_KEY)));
        BigDecimal discountValue = new BigDecimal(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        String voucherTypeName = String.valueOf(voucherObject.get(VOUCHER_TYPE_KEY));
        VoucherType voucherType = VoucherType.findVoucherType(voucherTypeName);
        return new Voucher(voucherId, discountValue, voucherType);
    };
    private final Function<Voucher, HashMap<String, Object>> voucherToObject = (voucher) -> {
        HashMap<String, Object> voucherObject = new HashMap<>();
        voucherObject.put(VOUCHER_ID_KEY, voucher.getVoucherId().toString());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue().toString());
        voucherObject.put(VOUCHER_TYPE_KEY, voucher.getVoucherType().name());
        return voucherObject;
    };

    public FileVoucherRepository(AppProperties appProperties, @Qualifier("voucher") JSONFileManager<UUID, Voucher> jsonFileManager) {
        this.filePath = appProperties.getVoucherFilePath();
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
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
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
