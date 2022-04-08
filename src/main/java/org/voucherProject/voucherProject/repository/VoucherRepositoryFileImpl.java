package org.voucherProject.voucherProject.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.voucher.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
//@Primary
public class VoucherRepositoryFileImpl implements VoucherRepository {

    private final FileWriter fileWriter = new FileWriter("voucherRepository.txt", true);
    private final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    private final FileReader fileReader = new FileReader("voucherRepository.txt");
    private final BufferedReader bufferedReader = new BufferedReader(fileReader);

    public VoucherRepositoryFileImpl() throws IOException {
    }

    /**
     * 일단 추후 구현
     */
    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        List<Voucher> vouchers = getVouchers();
        return vouchers.stream().filter(v -> v.getVoucherId().equals(voucherId)).limit(1).findFirst();
    }

    @Override
    public Voucher save(Voucher voucher) throws IOException {
        try {
            String saveFile = voucher.getVoucherId() + "," + voucher.getHowMuch() + "," + voucher.getVoucherType() + "," + voucher.getVoucherStatus();
            bufferedWriter.write(saveFile);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IOException();
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() throws IOException {
        List<Voucher> vouchers = getVouchers();
        return vouchers;
    }

    private List<Voucher> getVouchers() throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        String readLine = null;
        while ((readLine = bufferedReader.readLine()) != null) {
            String[] readLineSplit = readLine.split(",");
            Optional<Voucher> voucher = Optional.empty();
            // 새로 바우처 생성
            voucher = getVoucher(readLineSplit, voucher);
            // 초기 바우처는 상태가 valid이므로 사용되었으면 EXPIRED 변경
            checkVoucherStatus(readLineSplit, voucher);
            vouchers.add(voucher.get());
        }
        return vouchers;
    }

    private void checkVoucherStatus(String[] readLineSplit, Optional<Voucher> voucher) {
        if (readLineSplit[3].equalsIgnoreCase(String.valueOf(VoucherStatus.EXPIRED))) {
            voucher.get().useVoucher();
        }
    }

    private Optional<Voucher> getVoucher(String[] readLineSplit, Optional<Voucher> voucher) {
        if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.FIXED)) ) {
            voucher = Optional.of(new FixedAmountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1])));
        }
        if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.PERCENT)) ) {
            voucher = Optional.of(new PercentDiscountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1])));
        }
        return voucher;
    }
}
