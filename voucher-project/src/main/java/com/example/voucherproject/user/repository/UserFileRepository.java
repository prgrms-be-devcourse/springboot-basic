package com.example.voucherproject.user.repository;

import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class UserFileRepository implements UserRepository{
    private final MyReader reader;
    private final MyWriter writer;

    private final String ALL_USER_LIST = "user_allList.csv";
    private final String BLACK_USER_LIST = "user_blacklist.csv";

    @Override
    public User save(User user) {
        switch(user.getType()){
            case BLACK:
                writer.writeUser(user, BLACK_USER_LIST);
            case NORMAL:
                writer.writeUser(user, ALL_USER_LIST);
                break;
            default:
                log.info("[유저 저장 실패] 사유 : 유저 타입 미지정 -> See 유저정보 -> " + user);
                break;
        }
        return user;
    }

    @Override
    public List<User> getUserList(UserType type) {
        switch(type){
            case BLACK:
                return getUserList(BLACK_USER_LIST);
            case NORMAL:
                return getUserList(ALL_USER_LIST);
            default:
                log.info("[유저 리스트 불러오기 실패] 사유 : 존재하지 않는 유저타입");
                return null;
        }
    }

    private List<User> getUserList(String userListType) {
        List<User> users = new ArrayList<>();
        var allUser = reader.readLists(userListType);
        allUser.forEach(user ->{
            var userUUID = UUID.fromString(user.get(0));
            var userType = UserType.valueOf(user.get(1));
            var userName = user.get(2);
            users.add(new User(userUUID, userType, userName));
        });
        return users;
    }

}
