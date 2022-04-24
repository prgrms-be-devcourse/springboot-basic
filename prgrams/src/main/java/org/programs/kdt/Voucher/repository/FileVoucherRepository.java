package org.programs.kdt.Voucher.repository;

import lombok.Getter;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Utils.FileUtil;
import org.programs.kdt.Voucher.configure.FileProperty;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Profile("staging")
@Repository
@EnableConfigurationProperties
@Getter
public class FileVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private String filePath;

    private Map<UUID, Voucher> storage = new LinkedHashMap<>();

    public FileVoucherRepository(FileProperty fileProperty) {
        this.filePath = fileProperty.getVoucher();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    private Map<UUID, Voucher> fileReadToVoucherMap() {
        List<String> voucherStringList = FileUtil.readFileByList(filePath);
        Map<UUID, Voucher> voucherMap = new LinkedHashMap<>();

        for(String voucherString : voucherStringList) {
            String[] split = voucherString.split(",");
            UUID voucherId= UUID.fromString(split[0]);
            VoucherType voucherType = VoucherType.findVoucherType(split[1]);
            Long voucherValue = Long.parseLong(split[2]);
            LocalDateTime createdAt =  LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            voucherMap.put(voucherId, voucherType.getConstuructor().apply(voucherId, voucherValue, createdAt));
        }

        return voucherMap;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        boolean isVoucherId = storage.containsKey(voucher.getVoucherId());
        if (isVoucherId) {
            throw new DuplicationException(ErrorCode.DUPLICATION_VOUCHER_ID);
        }

        synchronized (this) {
            storage.put(voucher.getVoucherId(), voucher);
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher update(Voucher updateVoucher) {
        boolean isVoucherId = storage.containsKey(updateVoucher.getVoucherId());
        if (!isVoucherId) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
        }
        storage.put(updateVoucher.getVoucherId(), updateVoucher);
        return updateVoucher;
    }

    @Override
    public void deleteAll() {
        storage = new LinkedHashMap<>();
    }

    @Override
    public void delete(UUID uuid) {
        storage.remove(uuid);
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return storage.values().stream().filter(voucher -> voucher.getVoucherType().equals(voucherType)).collect(Collectors.toList());
    }

    @Override
    public boolean existId(UUID customerId) {
        return storage.containsKey(customerId);
    }

    private void fileWriteByVoucherList() {
        StringBuilder voucherListToString = new StringBuilder();
        storage.values().stream().forEach(voucher -> voucherListToString.append(voucherToCsvString(voucher) + "\n"));
        FileUtil.fileWriteLine(filePath, voucherListToString.toString());
    }
    public String voucherToCsvString(Voucher voucher) {
        return voucher.getVoucherId().toString() + "," + voucher.getVoucherType().getType()
                + "," + voucher.getValue();
    }

    @Override
    public void destroy() throws Exception {
        fileWriteByVoucherList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.storage = fileReadToVoucherMap();
    }
}
