package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.setting.VoucherProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConsoleListWriter {
    private final BlacklistProperties blacklistProperties;
    private final VoucherProperties voucherProperties;


    public ConsoleListWriter(BlacklistProperties blacklistProperties, VoucherProperties voucherProperties) {
        this.blacklistProperties = blacklistProperties;
        this.voucherProperties = voucherProperties;
    }

    public void write(String message) {
        System.out.println(message);
    }

    public void showListType() {
        write("=== Choosing list type ===");
        write("Type 'all' to list all vouchers");
        write("Type 'user' to list vouchers a specific user has");
        if (blacklistProperties.isBlacklistAllow()) {
            write("Type 'ban' to print out the blacklist");
        }
    }

    public void showWhichVoucherUser() {
        write("Type name to show vouchers a user have");
    }

    public void showList(VoucherListResponse voucherListResponse) {
        Map<UUID, Voucher> voucherHistory = voucherListResponse.getVoucherList();

        if (voucherHistory.isEmpty()) {
            write("list is empty.");
            return;
        }
        write("============== List of created vouchers ==============");
        write("type    uuid                                 discount");
        voucherHistory.entrySet().forEach(entry -> {
            UUID uuid = entry.getKey();
            Voucher voucher = entry.getValue();
            if (voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
                write(String.format("fixed   %s %s", uuid, fixedAmountVoucher.getAmount().getValue()));
                return;
            }

            if (voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
                write(String.format("percent %s %s%%", uuid, percentDiscountVoucher.getPercent().getValue()));
            }
        });

        write("type number to take vouher user the user");

    }

    public void showBlacklist(BlacklistResponse blacklistResponse) {
        Map<UUID, String> blacklist = blacklistResponse.getBlacklist();

        if (blacklist.isEmpty()) {
            write("list is empty.");
            return;
        }

        if (blacklistProperties.isBlacklistShowId()) {
            write("=========== blacklisted users ===========");
            write("uuid                                 name");
            blacklist.entrySet().forEach(entry -> {
                UUID uuid = entry.getKey();
                String name = entry.getValue();
                write(String.format("%s %s", uuid, name));
            });
            return;
        }

        AtomicInteger order = new AtomicInteger(1);
        write("=========== blacklisted users ===========");
        write("order name");
        blacklist.entrySet().forEach(entry -> {
            String name = entry.getValue();
            write(String.format("%d     %s", order.getAndIncrement(), name));
        });
    }
}
