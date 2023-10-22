package team.marco.vouchermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.model.User;
import team.marco.vouchermanagementsystem.service.BlacklistService;

import java.util.List;

@Controller
public class UserController {
    private final BlacklistService blacklistService;

    public UserController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public List<String> getBlacklistInfo() {
        return blacklistService.getBlacklist().stream()
                .map(User::getInfo)
                .toList();
    }
}
