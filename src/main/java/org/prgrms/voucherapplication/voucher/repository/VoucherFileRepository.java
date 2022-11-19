package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.service.CsvFileService;
import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.prgrms.voucherapplication.voucher.entity.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile("prod")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final File voucherFile;
    private final CsvFileService csvFileService;

    public VoucherFileRepository(@Value("${file-path.voucher}") String voucherFilePath, CsvFileService csvFileService) {
        this.voucherFile = new File(voucherFilePath);
        this.csvFileService = csvFileService;
    }

    @Override
    public void save(Voucher voucher) {
        String line = voucher.toString();
        csvFileService.write(voucherFile, line);
    }

    @Override
    public List<Voucher> findAll() {
        List<String> readFileLines = csvFileService.readFileLines(voucherFile);

        return fileLinesIntoVouchers(readFileLines);
    }

    private List<Voucher> fileLinesIntoVouchers(List<String> readFileLines) {
        List<Voucher> voucherList = new ArrayList<>();

        for (String voucherToString : readFileLines) {
            int voucherTypeNameEndIndex = voucherToString.indexOf("Voucher");
            String voucherTypeName = voucherToString.substring(0, voucherTypeNameEndIndex);
            VoucherType voucherType = VoucherType.of(voucherTypeName);

            String[] split = voucherToString.split("=");
            final String notNumber = "\\D";
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].replaceAll(notNumber, "");
            }

            UUID uuid = UUID.fromString(split[1]);
            int discount = Integer.parseInt(split[2]);
            Voucher voucher = voucherType.createVoucher(uuid, discount);
            voucherList.add(voucher);
        }

        return voucherList;
    }
}
