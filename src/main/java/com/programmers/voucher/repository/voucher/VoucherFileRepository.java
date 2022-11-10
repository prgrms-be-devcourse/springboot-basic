package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("local")
public class VoucherFileRepository implements VoucherRepository {
    private final FileWriter writer;
    private final String fileName;

    public VoucherFileRepository(@Value("${file.path.voucher}") String fileName) {
        this.fileName = fileName;
        try {
            writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            throw new RuntimeException("파일 생성에 실패하였습니다.");
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("파일에 바우처 저장하기 실패하였습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            String content;
            while ((content = buffer.readLine()) != null) {
                vouchers.add(toVoucher(content));
            }
        } catch (IOException e1) {
            throw new RuntimeException("파일에서 바우처를 불러오지 못했습니다.");
        }
        return vouchers;
    }

    private Voucher toVoucher(String content) {
        String[] contents = content.split("\t");
        VoucherType voucherType = VoucherType.toVoucherTypeByName(contents[0]);
        long discountValue = Long.parseLong(contents[2].substring(0, contents[2].length() - 2));
        Voucher voucher = voucherType.convertToVoucher(UUID.fromString(contents[1]), discountValue);
        return voucher;
    }
}
