package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.util.FileUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private static final String EMPTY = "EMPTY";
    private static final String FILE_PATH = System.getProperty("user.home") + "/voucher.csv";
    private static final String DELIMITER = ",";
    private final FileUtil fileUtil;
    private final Map<UUID, List<UUID>> customerVouchers;
    private final Map<UUID, Voucher> vouchers;

    public FileVoucherRepository(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
        this.customerVouchers = new ConcurrentHashMap<>();
        this.vouchers = initData();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        String voucherInfo = getVoucherInfo(voucher, EMPTY) + System.lineSeparator();
        fileUtil.saveToCsvFile(voucherInfo, FILE_PATH);

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
    public void registerVoucher(UUID customerId, UUID voucherId) {
        Voucher voucher = findById(voucherId).get();
        customerVouchers.computeIfAbsent((customerId), k -> new ArrayList<>())
                .add(voucherId);

        fileUtil.updateFile(getVoucherInfo(voucher, customerId.toString()), voucherId, FILE_PATH);
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return customerVouchers.getOrDefault(customerId, Collections.emptyList())
                .stream()
                .map(voucherId -> findById(voucherId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void removeVoucher(UUID voucherId) {
        Voucher voucher = findById(voucherId).get();
        customerVouchers.values().forEach(vouchers -> vouchers.remove(voucherId));
        fileUtil.updateFile(getVoucherInfo(voucher, EMPTY), voucherId, FILE_PATH);
    }

    @Override
    public Optional<UUID> findVoucherOwner(UUID voucherId) {
        return customerVouchers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(voucherId))
                .map(Map.Entry::getKey)
                .findAny();
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
        customerVouchers.values()
                .forEach(customerVoucherList -> customerVoucherList.remove(voucherId));

        fileUtil.deleteFileDataById(voucherId,FILE_PATH);
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
        customerVouchers.clear();
        fileUtil.clearDataFile(FILE_PATH);
    }

    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = vouchers.get(voucherId);
        voucher.updateVoucherInfo(voucherUpdateRequest);

        String customerId = customerVouchers.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(voucherId))
                .map(Map.Entry::getKey)
                .findFirst()
                .map(UUID::toString)
                .orElse(EMPTY);

        fileUtil.updateFile(getVoucherInfo(voucher, customerId), voucherId, FILE_PATH);
    }

    private String getVoucherInfo(Voucher voucher, String voucherOwnerId) {
        String voucherId = String.valueOf(voucher.getVoucherId());
        String discount = String.valueOf(voucher.getDiscount());
        String voucherType = String.valueOf(voucher.getVoucherType());
        String useStatusType = String.valueOf(voucher.getUseStatusType());
        String customerId = voucherOwnerId.equals(EMPTY) ?  EMPTY : voucherOwnerId;

        return String.join(DELIMITER, voucherId, discount, voucherType, useStatusType, customerId);
    }

    private Map<UUID, Voucher> initData() {
        fileUtil.createFile(FILE_PATH);
        Map<UUID, Voucher> loadedVouchers = new ConcurrentHashMap<>();
        List<String> loadedData = fileUtil.loadFromCsvFile(FILE_PATH);

        for (String data : loadedData) {
            String[] voucherInfo = data.split(DELIMITER);
            UUID voucherId = UUID.fromString(voucherInfo[0]);
            long discount = Long.parseLong(voucherInfo[1]);
            VoucherType voucherType = VoucherType.valueOf(voucherInfo[2]);
            UseStatusType useStatusType = UseStatusType.valueOf(voucherInfo[3]);
            String customerId = voucherInfo[4].equals(EMPTY) ? EMPTY : voucherInfo[4];

            Voucher voucher = switch (voucherType) {
                case FIXED -> new FixedDiscountVoucher(voucherId, discount, useStatusType);
                case PERCENT -> new PercentDiscountVoucher(voucherId, discount, useStatusType);
            };
            loadedVouchers.put(voucherId, voucher);

            if(!customerId.equals(EMPTY)) {
                customerVouchers.computeIfAbsent(UUID.fromString(customerId), k -> new ArrayList<>())
                        .add(voucherId);
            }
        }
        return loadedVouchers;
    }
}