package prgms.springbasic.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import prgms.springbasic.voucher.FixedAmountVoucher;
import prgms.springbasic.voucher.PercentDiscountVoucher;
import prgms.springbasic.voucher.Voucher;
import prgms.springbasic.voucher.VoucherType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String path = "src/main/VoucherData.csv";
    private final File file;

    public FileVoucherRepository() {
        file = new File(path);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                String[] splited = line.split(", ");
                if (splited[2].equals(String.valueOf(voucherId))) {
                    if (splited[0].equals("FixedAmountVoucher")) {
                        return Optional.of(new FixedAmountVoucher(UUID.fromString(splited[2]), Long.parseLong(splited[1])));
                    } else if (splited[0].equals("PercentDiscountVoucher")) {
                        return Optional.of(new PercentDiscountVoucher(UUID.fromString(splited[2]), Long.parseLong(splited[1])));
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(voucher.toString() + System.lineSeparator());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException exception) {
            logger.error("바우처 파일 레포지토리에 바우처를 추가하지 못했습니다. voucher -> {}", voucher.toString());
        }
        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] component = line.split(", ");
                if ("FixedAmountVoucher".equals(component[0]))
                    voucherList.add(new FixedAmountVoucher(UUID.fromString(component[2]), Long.parseLong(component[1])));

                else if ("PercentDiscountVoucher".equals(component[0]))
                    voucherList.add(new PercentDiscountVoucher(UUID.fromString(component[2]), Long.parseLong(component[1])));
            }
            reader.close();
        } catch (IOException exception) {
            logger.error("바우처 파일 레포지토리의 정보를 가져오지 못했습니다.");
        }

        return voucherList;
    }
}
