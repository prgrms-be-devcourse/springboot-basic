package org.prgms.management.io.blacklist;

import org.prgms.management.blacklist.vo.Blacklist;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void init() {
        System.out.println("메인목록으로 가려면 **back**를 입력해주세요.");
        System.out.println("블랙리스트 목록을 보려면 **blacklist**를 입력해주세요.\n");
        System.out.println("블랙리스트 삭제하려면 **delete**를 입력해주세요.\n");
    }

    @Override
    public void blacklists(List<Blacklist> blackList) {
        System.out.println("\n=== 블랙리스트 목록 ===");
        if (!blackList.isEmpty()) {
            blackList.forEach(v -> System.out.println(MessageFormat.format(
                    "{0} {1} {2}", v.blacklistId(),
                    v.customerId(), v.createdAt())));
        }
        System.out.println();
    }

    @Override
    public void delete(String msg) {
        System.out.println(MessageFormat.format("\n{0}\n", msg));
    }
}
