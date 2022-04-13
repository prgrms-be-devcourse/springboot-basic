package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.configutarion.FilePathConfiguration;
import org.prgrms.voucherapplication.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.entity.PercentDiscountVoucher;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 데이터를 파일로 관리하는 레포지터체
 */
@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final FilePathConfiguration configuration;

    public FileVoucherRepository(FilePathConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 바우처 id로 바우처를 찾는 메소드
     *
     * @param voucherId
     * @return Voucher 객체를 반환하고, 해당하는 바우처가 없으면 Optional로 감싼 null 반환
     * @throws IOException
     */
    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        return Optional.of(
                findAll().stream()
                        .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                        .findFirst()
                        .orElse(null)
        );
    }

    /**
     * 파일에 바우처를 추가
     *
     * @param voucher
     * @return
     * @throws IOException
     */
    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        File file = new File(configuration.getVoucherlist());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String voucherType;
        if (voucher instanceof FixedAmountVoucher) {
            voucherType = VoucherType.FixedAmount.name();
        } else {
            voucherType = VoucherType.PercentDiscount.name();
        }

        writer.write(MessageFormat.format("{0},{1},{2}", voucherType, voucher.getVoucherId(), voucher.getDiscountValue()));
        writer.write(System.lineSeparator());
        writer.flush();
        writer.close();

        return voucher;
    }

    /**
     * 파일에 저장된 모든 바우처 정보를 반환
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<Voucher> findAll() throws IOException {
        File file = new File(configuration.getVoucherlist());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Voucher> vouchers = new ArrayList<>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] voucherInfo = line.split(",");
            vouchers.add(restoreVoucher(voucherInfo[0], voucherInfo[1], voucherInfo[2]));
        }
        reader.close();

        return vouchers;
    }

    /**
     * 파일에 있는 바우처 정보를 Voucher 객체로 복구해서 반환
     *
     * @param type           바우처 타입
     * @param id             바우처 id
     * @param discountAmount 할인가 또는 할인액
     * @return
     */
    private Voucher restoreVoucher(String type, String id, String discountAmount) {
        if (type.equals(VoucherType.FixedAmount.name())) {
            return new FixedAmountVoucher(UUID.fromString(id), Long.valueOf(discountAmount));
        } else {
            return new PercentDiscountVoucher(UUID.fromString(id), Long.valueOf(discountAmount));
        }
    }
}
