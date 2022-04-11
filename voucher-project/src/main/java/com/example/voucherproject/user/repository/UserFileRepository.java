package com.example.voucherproject.user.repository;

import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.common.file.CSVReader;
import com.example.voucherproject.common.file.CSVWriter;
import com.example.voucherproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class UserFileRepository implements UserRepository{
    // TODO: file io interface로 변경하기
    private CSVReader csvReader = new CSVReader();
    private CSVWriter csvWriter = new CSVWriter();

    private final String ALL_USER_LIST = "user_allList.csv";
    private final String BLACK_USER_LIST = "user_blacklist.csv";

    @Override
    public User save(User user) {
        switch(user.getType()){
            case BLACK:
                csvWriter.userToCSV(user,BLACK_USER_LIST);
            case NORMAL:
                csvWriter.userToCSV(user,ALL_USER_LIST);
                break;
            default:
                log.warn("[유저 저장 실패] 사유 : 유저 타입 미지정 -> See 유저정보 -> " + user);
                break;
        }
        return user;
    }

    @Override
    public List<User> getUserList(UserType type) {
        switch(type){
            case BLACK:
                return getList(BLACK_USER_LIST);
            case NORMAL:
                return getList(ALL_USER_LIST);
            default:
                log.warn("[유저 리스트 불러오기 실패] 사유 : 존재하지 않는 유저타입");
                return null;
        }
    }

    // TODO: 여기서 유저를 생성해도 될까? -> 나는 된다고 생각함
    public List<User> getList(String listType) {
        List<User> users = new ArrayList<>();

        var allUser = csvReader.readUser(listType);
        allUser.stream().forEach(user ->{
            var userUUID = UUID.fromString(user.get(0));
            var userType = UserType.valueOf(user.get(1));
            var userName = user.get(2);
            users.add(new User(userUUID, userType, userName));
        });
        return users;
    }

}
