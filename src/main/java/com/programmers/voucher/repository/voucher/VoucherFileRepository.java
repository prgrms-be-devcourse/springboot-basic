package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class VoucherFileRepository implements VoucherRepository {

    private final String FAIL_CREATE_FILE = "파일 생성에 실패하였습니다.";
    private final String FAIL_SAVE_VOUCHER = "파일에 바우처 저장하기 실패하였습니다.";
    private final String FAIL_GET_VOUCHER = "파일에서 바우처를 불러오지 못했습니다.";
    private final FileWriter writer;
    private final String fileName;

    public VoucherFileRepository(@Value("${file.path.voucher}") String fileName) {
        this.fileName = fileName;
        try {
            writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            throw new RuntimeException(FAIL_CREATE_FILE);
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(FAIL_SAVE_VOUCHER);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) {
            return buffer.lines()
                    .map(line -> line.split("\t"))
                    .map(content -> VoucherType.toVoucherTypeByName(content[0])
                            .convertToVoucher(UUID.fromString(content[1]),
                                    Long.parseLong(content[2].substring(0, content[2].length() - 2))))
                    .collect(Collectors.toList());
        } catch (IOException e1) {
            throw new RuntimeException(FAIL_GET_VOUCHER);
        }
    }
}
