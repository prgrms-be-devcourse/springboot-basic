package org.voucherProject.voucherProject.repository;

import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * customer_blacklist.csv 파일을 만들고 스프링 애플리케이션에서 해당 파일을 읽을 수 있고 블랙 리스트조회 할 수있다
 */
@Repository
public class BlackListRepository {

    private List<User> getVouchers() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("customer_blacklist.csv"));
        List<User> blackList = new ArrayList<>();
        String readLine = null;
        while ((readLine = bufferedReader.readLine()) != null) {
            String[] readLineSplit = readLine.split(",");
            blackList.add(new User(UUID.fromString(readLineSplit[0]), readLineSplit[1], readLineSplit[2]));
        }
        return blackList;
    }


}
