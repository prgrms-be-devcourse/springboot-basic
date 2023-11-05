package team.marco.voucher_management_system.console_app.controller;

import java.util.List;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.model.BlacklistUser;
import team.marco.voucher_management_system.service.BlacklistService;
import team.marco.voucher_management_system.util.Console;

@Component
public class ConsoleBlacklistController {
    private static final String INFO_DELIMINATOR = "\n";

    private final BlacklistService blacklistService;

    public ConsoleBlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public void blacklist() {
        List<String> blacklist = blacklistService.getBlacklist()
                .stream()
                .map(BlacklistUser::getInfo)
                .toList();
        String joinedString = String.join(INFO_DELIMINATOR, blacklist);

        if (!joinedString.isBlank()) {
            Console.print(joinedString);
        }

        Console.print("조회가 완료되었습니다.");
    }
}
