package com.example.voucherproject.common;

import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.model.VoucherType;
import com.example.voucherproject.wallet.model.Wallet;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Helper {
    public class VoucherHelper{
        public static Voucher makeVoucher( VoucherType type, long amount){
            return makeVoucherWithId(UUID.randomUUID(), amount, type);
        }
        public static Voucher makeVoucherWithId(UUID id, long amount, VoucherType type){
            return new Voucher(id, type, amount,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        }
    }

    public class UserHelper{
        public static User makeUser(String name, UserType type){
            return makeUserWithId(UUID.randomUUID(), name, type);
        }
        public static UserDTO.Create makeUserDTO(String name, UserType type){
            var user =  new UserDTO.Create();
            user.setName(name);
            user.setType(type);
            return user;
        }
        public static User makeUserWithId(UUID id, String name, UserType type){
            return new User(id, type, name,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        }
    }

    public class WalletHelper{
        public static Wallet makeWallet(){
            return new Wallet(UUID.randomUUID(),UUID.randomUUID(), UUID.randomUUID(),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        }
        public static Wallet makeWalletTwoIds(UUID userId, UUID voucherId){
            return new Wallet(UUID.randomUUID(),userId, voucherId,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        }
        public static Wallet makeWalletThreeIds(UUID id, UUID userId, UUID voucherId){
            return new Wallet(id, userId, voucherId,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                    LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        }
    }

}
