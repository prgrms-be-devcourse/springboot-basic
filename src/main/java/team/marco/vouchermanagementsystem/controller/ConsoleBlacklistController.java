package team.marco.vouchermanagementsystem.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.model.BlacklistUser;
import team.marco.vouchermanagementsystem.service.BlacklistService;
import team.marco.vouchermanagementsystem.util.Console;

@Controller
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
