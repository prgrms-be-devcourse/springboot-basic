package org.prgrms.kdt.user.repository;

import org.prgrms.kdt.user.domain.BannedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * TODO: UserRepository interface 요구사항 더 들어오면 만들기 ㅡㅡ
 */
// @Profile({"local", "default"})// 용도를 구별
@Repository
public class FileUserRepository implements UserRepository {

    @Autowired
    ApplicationContext resourceLoader;

    @Override
    public List<BannedCustomer> getBlackListCSV() throws IOException {

        Resource resource = resourceLoader.getResource("file:user/blacklist.csv");

        // TODO: Parser를 캡슐화하면?
        var items = Files.readAllLines(resource.getFile().toPath());
        return items.stream()
                .map(line -> Arrays.asList(line.split(",")))
                .map(list -> new BannedCustomer(
                        UUID.fromString(list.get(BANNED_CUSTOMER_INDEX.BANNED_UUID.value())),
                        list.get(BANNED_CUSTOMER_INDEX.EMAIL.value()),
                        list.get(BANNED_CUSTOMER_INDEX.NAME.value()),
                        list.get(BANNED_CUSTOMER_INDEX.DESCRIPTION.value())))
                .collect(Collectors.toList());
    }


}
