package org.prgms.voucheradmin.domain.voucher.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository{
    private final VoucherAdminProperties voucherAdminProperties;

    public FileVoucherRepository(VoucherAdminProperties voucherAdminProperties) {
        this.voucherAdminProperties = voucherAdminProperties;
    }

    @Override
    public Voucher save(Voucher voucher) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(voucherAdminProperties.getVoucherFilePath(),true));
        bw.write(voucher.toString());
        bw.newLine();
        bw.flush();

        return voucher;
    }

    @Override
    public List<Voucher> getAll() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(voucherAdminProperties.getVoucherFilePath()));
        List<Voucher> vouchers = new ArrayList<>();

        for(String record : br.lines().collect(Collectors.toList())) {
            StringTokenizer st = new StringTokenizer(record,"\t");

            UUID voucherId = UUID.fromString(st.nextToken());
            VoucherTypes voucherType = VoucherTypes.valueOf(st.nextToken());
            long amount = Long.parseLong(st.nextToken());

            switch (voucherType) {
                case FIXED_AMOUNT:
                    vouchers.add(new FixedAmountVoucher(voucherId, amount));
                    break;
                default:
                    vouchers.add(new PercentageDiscountVoucher(voucherId, amount));
                    break;
            }
        }

        return vouchers;
    }
}
