package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.prgrms.voucherapplication.voucher.service.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile("file")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final File voucherFile;
    private final CsvFileService csvFileService;

    public VoucherFileRepository(@Value("${file-path.voucher}") String voucherFilePath, CsvFileService csvFileService) {
        this.voucherFile = new File(voucherFilePath);
        this.csvFileService = csvFileService;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String line = voucher.toString();
        csvFileService.write(voucherFile, line);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<String> readFileLines = csvFileService.readFileLines(voucherFile);

        return fileLinesIntoVouchers(readFileLines);
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    private List<Voucher> fileLinesIntoVouchers(List<String> readFileLines) {
        List<Voucher> voucherList = new ArrayList<>();

        for (String voucherToString : readFileLines) {
            int voucherTypeNameEndIndex = voucherToString.indexOf("Voucher");
            String voucherTypeName = voucherToString.substring(0, voucherTypeNameEndIndex);
            VoucherType voucherType = VoucherType.of(voucherTypeName);

            String voucherId = parseIndexOf(voucherToString, "uuid=", ", discount=");
            String discountStr = parseIndexOf(voucherToString, "discount=", ", voucherType=");
            String createdAtStr = parseIndexOf(voucherToString, "createdAt=", "}");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

            UUID uuid = UUID.fromString(voucherId);
            int discount = Integer.parseInt(discountStr);
            LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
            Voucher voucher = voucherType.createVoucher(uuid, discount, createdAt);
            voucherList.add(voucher);
        }

        return voucherList;
    }

    private String parseIndexOf(String string, String start, String end) {
        int startIndex = string.indexOf(start);
        int endIndex = string.indexOf(end);
        return string.substring(startIndex + start.length(), endIndex);
    }
}
