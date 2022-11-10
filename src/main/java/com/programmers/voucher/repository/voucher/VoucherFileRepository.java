package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;

import java.io.IOException;
import java.util.List;

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
            writer.write(voucher.toString()+"\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("파일에 바우처 저장하기 실패하였습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
