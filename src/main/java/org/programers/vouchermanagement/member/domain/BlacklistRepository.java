package org.programers.vouchermanagement.member.domain;

import org.programers.vouchermanagement.util.Converter;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {

    private static final Path file = Paths.get("src/main/resources/blacklist.csv");

    static {
        try {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAllByStatus(MemberStatus status) {
        if (status.isBlack()) {
            return findAllByBlackStatus();
        }
        return new ArrayList<>();
    }

    private List<Member> findAllByBlackStatus() {
        try {
            return Files.readAllLines(file).stream()
                    .map(Converter::toMember)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 블랙 회원이 조회되지 않았습니다.");
        }
    }
}
