package org.voucherProject.voucherProject.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.voucher.entity.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class FileVoucherDao implements VoucherDao {

    private final String FILE_VOUCHER_REPO_PATH = "src/main/resources/voucherRepository.txt";

    private final FileWriter fileWriter = new FileWriter(FILE_VOUCHER_REPO_PATH, true);
    private final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

    public FileVoucherDao() throws IOException {
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
            String saveFile = voucher.getVoucherId() + "," + voucher.getHowMuch() + "," + voucher.getVoucherType()
                    + "," + voucher.getVoucherStatus() + "," + voucher.getCreatedAt() + "," + voucher.getCustomerId();
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
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteAll() {
        try {
            new FileOutputStream(FILE_VOUCHER_REPO_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteOneByCustomerId(UUID customerId, UUID voucherId) {

    }

    private List<Voucher> getVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_VOUCHER_REPO_PATH));
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                String[] readLineSplit = readLine.split(",");
                // file 정보 기반으로 새 바우처 생성
                Voucher voucher = getVoucher(readLineSplit);
                vouchers.add(voucher);
            }
        } catch (IOException e) {
            log.error("잘못된 입력입니다.");
        }
        return vouchers;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    private Voucher getVoucher(String[] readLineSplit) {
        VoucherType voucherType = VoucherType.valueOf(readLineSplit[2].toUpperCase());
        return voucherType.createVoucher(UUID.fromString(readLineSplit[0]),
                Long.parseLong(readLineSplit[1]),
                VoucherStatus.valueOf(readLineSplit[3].toUpperCase()),
                LocalDateTime.parse(readLineSplit[4], formatter),
                UUID.fromString(readLineSplit[5]));
    }
}
