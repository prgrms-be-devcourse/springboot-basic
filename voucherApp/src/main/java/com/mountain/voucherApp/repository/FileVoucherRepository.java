package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.properties.FileProperties;
import com.mountain.voucherApp.voucher.FixedAmountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final FileProperties fileProperties;
    private final File listFile;
    private List<Voucher> list = null;

    public FileVoucherRepository(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
         this.listFile = new FileSystemResource(getFullPath()).getFile();
    }

    @Override
    public List<Voucher> findAll() {
        init();
        return list;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        init();
        try {
            FileWriter fw = new FileWriter(listFile.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder content = new StringBuilder(voucher.getVoucherId().toString())
                    .append(",")
                    .append(voucher.getAmount())
                    .append('\n');
            list.add(makeVoucher(voucher.getVoucherId().toString(), voucher.getAmount()));
            bw.write(content.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("file insert error");
        }
        return voucher;
    }

    private void init() {
        if (list == null) {
            list = new ArrayList<>();
            try {
                makeFile();
                BufferedReader inFile = new BufferedReader(new FileReader(getFullPath()));
                String line = null;
                while ((line = inFile.readLine()) != null) {
                    // TODO 하드코딩을 사용하지 않고 VOUCHER 인스턴스를 생성하고 List 추가하기.
                    // System.out.print(line);
                    String[] data = line.split(",");
                    list.add(makeVoucher(data[0], Long.valueOf(data[1])));
                }
                inFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("file read error");
            }
        }
    }

    // TODO 다양한 바우처의 인스턴스를 생성해야 함
    private FixedAmountVoucher makeVoucher(String uuid, long amount) {
        return new FixedAmountVoucher(UUID.fromString(uuid), amount);
    }

    private void makeFile() throws IOException {
        if (!listFile.exists()) {
            listFile.getParentFile().mkdir();
            listFile.createNewFile();
            System.out.println("create new file");
        }
    }

    private String getFullPath() {
        return fileProperties.getDir() + "/" + fileProperties.getFileName();
    }
}
