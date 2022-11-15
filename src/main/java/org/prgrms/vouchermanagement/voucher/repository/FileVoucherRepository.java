package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* TODO : 파일이 저장소 자체이기 때문에 파일의 입출력도 이 클래스에서 관리하는게 맞을까요?
*   csv로 저장하기 위해 입력값을 파싱해주는 로직과 파일에 읽고 쓰는 로직이 다 들어가있어
*  너무 많은 책임을 담당하는 것이 아닌지 궁금합니다.
*  확장성까지 고려하여 csv 파일에 값을 입출력하는 클래스를 따로 분리하는게 맞을까요?
* */
public class FileVoucherRepository implements VoucherRepository {

    private final String path;

    public FileVoucherRepository(@Value("${repository.file.voucher.path}") String path) {
        this.path = path;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            List<String> vouchers = Files.readAllLines(Paths.get(path));
            vouchers.stream()
                    .filter(voucherInfo -> {
                        String voucherIdCandidate = voucherInfo.split(",")[0];
                        return voucherIdCandidate.equals(voucherId.toString());
                    })
                    .findFirst()
                    .ifPresentOrElse(voucherInfo -> {
                        String[] infoArr = voucherInfo.split(",");
                        VoucherType.createVoucher(UUID.fromString(infoArr[0]), infoArr[1], Integer.parseInt(infoArr[2]));
                    }, Optional::empty);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), true));) {
            writer.write(parseCsvFormat(voucher.getVoucherId().toString(), voucher.getVoucherType().name(), String.valueOf(voucher.getDiscountAmount())));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            List<String> voucherInfos = Files.readAllLines(Paths.get(path));
            voucherInfos.forEach(info -> vouchers.add(createVoucher(info.split(","))));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return vouchers;
    }

    private String parseCsvFormat(String... input) {
        return String.join(",", input) + System.lineSeparator();
    }

    private Voucher createVoucher(String[] voucherInfos) {
        UUID uuid = UUID.fromString(voucherInfos[0]);
        String voucherType = voucherInfos[1];
        int discountAmount = Integer.parseInt(voucherInfos[2]);

        return VoucherType.createVoucher(uuid, voucherType, discountAmount);
    }

    @Override
    public void clear() {
        try {
            Files.writeString(Paths.get(path), "");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
