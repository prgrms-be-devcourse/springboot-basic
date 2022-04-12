package org.prgms.kdt.application.voucher.repository;

import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    @Value("${file.voucher.path}")
    private String filePath;

    @Override
    public UUID save(Voucher voucher) {
        String data = formatData(voucher);
        fileWrite(data);
        return voucher.getVoucherId();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = fileRead();
        return list;
    }

    private List<Voucher> fileRead() {
        List<Voucher> list = new ArrayList<>();
        String str;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((str = bufferedReader.readLine()) != null) {
                String[] data = str.split("/");
                createVoucherList(list, data);
            }
        } catch (IOException e) {
            logger.error("{}", e);
            e.printStackTrace();
        }
        return list;
    }

    private void createVoucherList(List<Voucher> list, String[] data) {
        VoucherType voucherType = VoucherType.findVoucherType(data[0]);
        UUID voucherId = UUID.fromString(data[1]);
        switch (voucherType) {
            case FIXED_AMOUNT:
                list.add(new FixedAmountVoucher(voucherId));
                break;
            case PERCENT_DISCOUNT:
                list.add(new PercentDiscountVoucher(voucherId));
                break;
            default:
                break;
        }
    }

    private void fileWrite(String data) {
        File file = new File(filePath);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("{}", e);
            e.printStackTrace();
        }
    }

    private String formatData(Voucher voucher) {
        return MessageFormat.format("{0}/{1}", voucher.getVoucherType().getType(), voucher.getVoucherId());
    }
}
