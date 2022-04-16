package org.voucherProject.voucherProject.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.voucher.entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
//@Primary
public class FileVoucherRepository implements VoucherRepository {

    private final String FILE_VOUCHER_REPO_PATH = "src/main/resources/voucherRepository.txt";

    private final FileWriter fileWriter = new FileWriter(FILE_VOUCHER_REPO_PATH, true);
    private final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    public FileVoucherRepository() throws IOException {
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = getVouchers();
        return vouchers.stream().filter(v -> v.getVoucherId().equals(voucherId)).limit(1).findFirst();
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            validSameId(voucher);
            String saveFile = voucher.getVoucherId() + "," + voucher.getHowMuch() + "," + voucher.getVoucherType() + "," + voucher.getVoucherStatus();
            bufferedWriter.write(saveFile);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }


    private void validSameId(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new RuntimeException("동일한 아이디가 존재합니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = getVouchers();
        return vouchers;
    }

    @Override
    public void deleteAll() {
        try {
            new FileOutputStream(FILE_VOUCHER_REPO_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private List<Voucher> getVouchers() {

        List<Voucher> vouchers = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_VOUCHER_REPO_PATH));
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                String[] readLineSplit = readLine.split(",");
                Optional<Voucher> voucher = Optional.empty();
                // file 정보 기반으로 새 바우처 생성
                voucher = getVoucher(readLineSplit, voucher);
                // 초기 바우처는 상태가 valid이므로 사용되었으면 EXPIRED 변경
                checkVoucherStatus(readLineSplit, voucher);
                vouchers.add(voucher.get());
            }
        } catch (IOException e) {
            log.error("잘못된 입력입니다.");
        }
        return vouchers;
    }

    private void checkVoucherStatus(String[] readLineSplit, Optional<Voucher> voucher) {
        if (readLineSplit[3].equalsIgnoreCase(String.valueOf(VoucherStatus.EXPIRED))) {
            voucher.get().useVoucher();
        }
    }

    private Optional<Voucher> getVoucher(String[] readLineSplit, Optional<Voucher> voucher) {
        if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.FIXED))) {
            voucher = Optional.of(new FixedAmountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1])));
        }
        if (readLineSplit[2].equalsIgnoreCase(String.valueOf(VoucherType.PERCENT))) {
            voucher = Optional.of(new PercentDiscountVoucher(UUID.fromString(readLineSplit[0]), Long.parseLong(readLineSplit[1])));
        }
        return voucher;
    }
}
