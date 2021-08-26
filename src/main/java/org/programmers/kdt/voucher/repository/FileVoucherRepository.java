package org.programmers.kdt.voucher.repository;


import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository  {
    // FIXME : 데이터 양이 많아지면 메모리를 너무 많이 차지하거나 오류가 발생할 수 있음.
    private final Map<UUID, Voucher> cache = new ConcurrentHashMap<>();
    // TODO : JSON 파일에 읽고 쓰도록 바꾸기
    // TODO : YAML 파일로부터 경로 및 파일명을 읽어들여 전달해주는 VoucherPropeties 클래스를 정의하고 이곳을 통해 path와 file을 받아오도록 수정하기
    private final File file = new File("VoucherData.txt");

    public FileVoucherRepository() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        while (null != (line = bufferedReader.readLine())) {
            String[] data = line.split(" ");
            UUID uuid = UUID.fromString(data[0]);
            VoucherType voucherType = VoucherType.of(data[1]);
            long discount = Long.parseLong(data[2]);

            switch (voucherType) {
                case FIXED -> cache.put(uuid, new FixedAmountVoucher(uuid, discount));
                case PERCENT -> cache.put(uuid, new PercentDiscountVoucher(uuid, discount));
            }
        }

        bufferedReader.close();
        fileReader.close();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(cache.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        cache.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> deleteVoucher(UUID voucherId) {
        return Optional.ofNullable(cache.remove(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(cache.values());
    }

    @PreDestroy
    private void updateData() throws IOException {
        // FIXME : 매번 처음부터 다시 써야 하는데, 제거된 부분만 찾아서 없애는 방법을 찾아보자.
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
        for (Voucher voucher : cache.values()) {
            bufferedWriter.write(voucher.getVoucherId() + " " + voucher.getVoucherType() + " " + voucher.getDiscount());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }
}
