package org.prgrms.kdtbespring.fileio;

import org.prgrms.kdtbespring.voucher.FixedAmountVoucher;
import org.prgrms.kdtbespring.voucher.PercentDiscountVoucher;
import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VoucherReadCsvFile implements ReadFile {
    @Override
    public List<Voucher> readFile() {
        List<Voucher> csvList = new ArrayList<>();
        Path directoryPath = Paths.get("localStorage");
        File csv = new File(directoryPath + "\\voucher.csv");

        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
                String[] voucherString = line.split(",");

                // String 배열 타입을 voucher 타입에 맞게 변환
                Optional<Voucher> voucher = parseVoucher(voucherString);
                voucher.ifPresent(csvList::add);
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
                    return parseVoucher(voucherString);
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
        try {
            VoucherType voucherType = VoucherType.valueOf(voucherString[1]);
            System.out.println("voucherType = " + voucherType);
            UUID uuid = UUID.fromString(voucherString[2]);
            long discountValue = Long.parseLong(voucherString[3]);
            Voucher voucher = voucherType.voucherCreate(uuid, discountValue);
            return Optional.of(voucher);
        }catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }
}

