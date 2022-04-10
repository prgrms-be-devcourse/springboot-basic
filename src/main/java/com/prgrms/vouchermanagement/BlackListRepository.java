package com.prgrms.vouchermanagement;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BlackListRepository {
    private final ApplicationContext ac;

    public BlackListRepository(ApplicationContext ac) {
        this.ac = ac;
    }

    /**
     * black_list.csv 파일을 읽어서 Member 타입의 List 로 변환하여 반환한다.
     */
    public List<Member> findAll() throws IOException {
        Resource resource = ac.getResource("file:black_list.csv");
        File file = resource.getFile();
        return Files.readAllLines(file.toPath()).stream().
                map(Member::new).collect(Collectors.toList());
    }
}
