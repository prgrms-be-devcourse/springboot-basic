package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.response.BlacklistResponse;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.setting.BlacklistProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ConsoleListWriter {
    private final BlacklistProperties blacklistProperties;


    public ConsoleListWriter(BlacklistProperties blacklistProperties) {
        this.blacklistProperties = blacklistProperties;
    }

    public void write(String message) {
        System.out.println(message);
    }

    public void showListType() {
        write("=== Choosing list type ===");
        write("Type 'all' to list all vouchers");
        write("Type 'user' to list vouchers a specific user has");
        write("Type 'owner' to show the owner of specific voucher");
        if (blacklistProperties.isBlacklistAllow()) {
            write("Type 'ban' to print out the blacklist");
        }
        write("Type 'back' to return to previous step");

    }

    public void showWhichUser() {
        write("=== Choosing the user ===");
        write("Type name to show vouchers a user have");
    }

    public void showVoucherList(VoucherListResponse voucherListResponse) {
        List<Voucher> voucherHistory = voucherListResponse.getVoucherList();

        if (voucherHistory.isEmpty()) {
            showEmptyListMessage();
            return;
        }

        write("============== List of vouchers ==============");
        write("No.  Type     UUID                                Discount");

        for (int i = 0; i < voucherHistory.size(); i++) {
            Voucher voucher = voucherHistory.get(i);
            UUID uuid = voucher.getVoucherId();
            String discountType;
            String discountValue;

            if (voucher.getDiscountStrategy() instanceof FixedAmountDiscountStrategy fixedAmountDiscountStrategy) {
                discountType = "Fixed";
                discountValue = String.valueOf(fixedAmountDiscountStrategy.getAmount().getValue());
            } else if (voucher.getDiscountStrategy() instanceof PercentDiscountStrategy percentDiscountStrategy) {
                discountType = "Percent";
                discountValue = String.format("%s%%", percentDiscountStrategy.getPercent().getValue());
            } else {
                throw new NoSuchVoucherTypeException("no such voucher type");
            }

            int rowNumber = i + 1;
            write(String.format("%-4d %-7s %s %s", rowNumber, discountType, uuid, discountValue));
        }
    }

    public void showUserList(UserListResponse userListResponse) {
        List<User> userList = userListResponse.getUserList();

        if (userList.isEmpty()) {
            showEmptyListMessage();
            return;
        }

        write("============== List of users ==============");
        write("uuid                                 name");
        userList.forEach(user -> write(String.format("%s %s", user.getUserId(), user.getUsername())));
    }

    public void showBlacklist(BlacklistResponse blacklistResponse) {
        Map<UUID, String> blacklist = blacklistResponse.getBlacklist();

        if (blacklist.isEmpty()) {
            showEmptyListMessage();
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

    public void showUser(UserResponse userResponse) {
        User user = userResponse.getUser();

        write("=== Successfully retrieved the voucher owner ===");
        write(MessageFormat.format("user id : {0}", user.getUserId()));
        write(MessageFormat.format("username : {0}", user.getUsername()));
    }

    public void showWhichVoucher() {
        write("=== Choosing the voucher ===");
        write("Type row number to show the owner of the voucher");
    }

    public void showEmptyListMessage() {
        write("list is empty.");
    }
}
