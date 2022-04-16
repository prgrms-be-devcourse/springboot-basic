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
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;
import org.springframework.stereotype.Repository;

/**
 * 생성된 바우처를 파일에 저장하거나 파일에 저장된 바우처 목록의 반환을 담당하는 클래스입니다.
 **/
@Repository
public class FileVoucherRepository implements VoucherRepository{
    private final VoucherAdminProperties voucherAdminProperties;

    public FileVoucherRepository(VoucherAdminProperties voucherAdminProperties) {
        this.voucherAdminProperties = voucherAdminProperties;
    }

    /**
     * 생성된 바우처를 파일에 저장하는 메서드 입니다.
     **/
    @Override
    public Voucher create(Voucher voucher) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(voucherAdminProperties.getVoucherFilePath(),true));
        bw.write(voucher.toString());
        bw.newLine();
        bw.flush();

        return voucher;
    }

    /**
     * 바우처들 파일에서 읽어와 각각 종류에 맞는 entity에 매핑해준 후 바우처 목록을 반환하는 메서드입니다.
     **/
    @Override
    public List<Voucher> getAll() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(voucherAdminProperties.getVoucherFilePath()));
        List<Voucher> vouchers = new ArrayList<>();

        for(String record : br.lines().collect(Collectors.toList())) {
            StringTokenizer st = new StringTokenizer(record,"\t");

            UUID voucherId = UUID.fromString(st.nextToken());
            VoucherType voucherType = VoucherType.valueOf(st.nextToken());
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
