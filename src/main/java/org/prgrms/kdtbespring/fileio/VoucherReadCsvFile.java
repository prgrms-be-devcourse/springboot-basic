package org.prgrms.kdtbespring.fileio;

import org.prgrms.kdtbespring.voucher.FixedAmountVoucher;
import org.prgrms.kdtbespring.voucher.Voucher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherReadCsvFile implements ReadFile {
    @Override
    public List<Voucher> readFile() {
        List<Voucher> csvList = new ArrayList<>();
        File csv = new File("C:\\dev\\kdt\\file\\voucher.csv");
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
                String[] voucherString = line.split(",");

                // String 배열 타입을 voucher 타입에 맞게 변환
                Optional<Voucher> voucher = parseVoucher(voucherString);
                if (voucher.isPresent()) {
                    csvList.add(voucher.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return csvList;
    }

    @Override
    public Optional<Voucher> readRecord(UUID voucherId) {
        File csv = new File("C:\\dev\\kdt\\file\\voucher.csv");
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
                String[] voucherString = line.split(",");
                System.out.println("voucherId : " + voucherId);
                // 만약 찾는 voucherId와 같은 값이 있으면 해당 바우처 리턴
                if (voucherString[2].equals(voucherId.toString())) {
                    Optional<Voucher> voucher = parseVoucher(voucherString);
                    return voucher;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return Optional.empty();
    }

    // String[]을 받아 각각의 값을 voucher에 맞는 값으로 변환하여 객체 생성
    private Optional<Voucher> parseVoucher(String[] voucherString) {
        Voucher voucher;
        if (voucherString[1].equals("FixedAmountVoucher")) {
            UUID uuid = UUID.fromString(voucherString[2]);
            long amount = Long.parseLong(voucherString[3]);
            voucher = new FixedAmountVoucher(uuid, amount);
            return Optional.of(voucher);
        } else if (voucherString[1].equals("PercentDiscountVoucher")) {
            UUID uuid = UUID.fromString(voucherString[2]);
            long percent = Long.parseLong(voucherString[3]);
            voucher = new FixedAmountVoucher(uuid, percent);
            return Optional.of(voucher);
        } else {
            // 형식에 맞는 Voucher 타입이 없는 경우
            return Optional.empty();
        }
    }
}

