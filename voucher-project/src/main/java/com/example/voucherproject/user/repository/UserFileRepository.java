package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class UserFileRepository implements UserRepository{
    private final MyReader reader;
    private final MyWriter writer;

    @Value(value = "${kdt.path.user.all}")
    private String ALL_USER_LIST;
    @Value(value = "${kdt.path.user.black}")
    private String BLACK_USER_LIST;

    @Override
    public User insert(User user) {
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
    public List<User> findHavingTypeAll(UserType type) {
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

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return getUserList(ALL_USER_LIST).size();
    }

    private List<User> getUserList(String userListType) {
        List<User> users = new ArrayList<>();
        var allUser = reader.readLists(userListType);
        allUser.forEach(user ->{
            var userUUID = UUID.fromString(user.get(0));
            var userType = UserType.valueOf(user.get(1));
            var userName = user.get(2);
            var createdAt = LocalDateTime.parse(user.get(3), DateTimeFormatter.ISO_LOCAL_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);

            users.add(new User(userUUID, userType, userName, createdAt));
        });
        return users;
    }
}
