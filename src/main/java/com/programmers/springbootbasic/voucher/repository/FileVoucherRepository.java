package com.programmers.springbootbasic.voucher.repository;

import com.programmers.springbootbasic.exception.NotFoundException;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final Path path;

    public FileVoucherRepository(@Value("${file.voucher.file-path}") String filePath) {
        this.path = Paths.get(filePath);
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true)); //path?
            writer.write(voucher.toString());
            writer.newLine();

            writer.flush();
        } catch (IOException ignored) {
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            if (Files.exists(path)) {
                List<Voucher> vouchers = Files.readAllLines(path).stream()
                        .map(this::extractVoucher)
                        .toList();

                voucherList.addAll(vouchers);
            }
        } catch (IOException ignored) {
        }

        return Collections.unmodifiableList(voucherList);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return findAll().stream()
                .filter(voucher -> Objects.equals(voucher.getVoucherId(), id))
                .findAny()
                .map(Optional::of)
                .orElseThrow(() -> new NotFoundException("[ERROR] 바우처가 존재하지 않습니다."));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return findAll().stream()
                .filter(voucher -> Objects.equals(voucher.getVoucherType(), voucherType))
                .collect(Collectors.toList());
    }

    @Override
    public Voucher update(Voucher voucher) {
        List<Voucher> vouchers = new ArrayList<>(findAll());

        for (int index = 0; index < vouchers.size(); index++) {
            UUID voucherId = vouchers.get(index).getVoucherId();

            if (voucherId.equals(voucher.getVoucherId())) {
                vouchers.set(index, voucher);
                break;
            }
        }

        deleteAll();
        vouchers.forEach(this::save);
        return voucher;
    }

    @Override
    public void deleteById(UUID id) {
        List<Voucher> vouchers = new ArrayList<>(findAll());

        Voucher deleteVoucher = vouchers.stream()
                .filter(voucher -> Objects.equals(voucher.getVoucherId(), id))
                .findAny()
                .get();

        vouchers.remove(deleteVoucher);
        deleteAll();
        vouchers.forEach(this::save);
    }

    @Override
    public void deleteAll() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            writer.write("");

            writer.flush();
        } catch (IOException ignored) {
        }
    }

    public Voucher extractVoucher(String line) {
        line = line.replace(" ", "");
        String[] voucherInfo = line.split("\\[|\\]|=|,");

        String type = voucherInfo[2].toLowerCase();
        UUID id = UUID.fromString(voucherInfo[4]);
        long discountValue = Long.parseLong(voucherInfo[6]);
        String name = voucherInfo[8];

        return VoucherType.createVoucher(type, id, name, discountValue);
    }
}
