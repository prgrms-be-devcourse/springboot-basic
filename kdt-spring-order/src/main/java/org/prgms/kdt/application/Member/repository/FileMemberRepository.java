package org.prgms.kdt.application.Member.repository;

import org.prgms.kdt.application.Member.domain.Member;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileMemberRepository implements MemberRepository{

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    @Value("${file.member.blacklist.path}")
    private String filePath;

    @Override
    public List<Member> getBlacklist() {
        List<Member> list = fileRead();
        return list;
    }

    private List<Member> fileRead() {
        List<Member> list = new ArrayList<>();
        String row;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((row = bufferedReader.readLine()) != null) {
                String[] data = row.split("/");
                list.add(new Member(UUID.fromString(data[0]), data[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
