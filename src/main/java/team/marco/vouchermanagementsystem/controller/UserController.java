package team.marco.vouchermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.service.BlacklistService;

import java.util.List;

import static java.text.MessageFormat.format;

@Controller
public class UserController {
    private final BlacklistService blacklistService;

    public UserController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    public List<String> getBlacklistInfo() {
        return blacklistService.getBlacklist().stream()
                .map(dto -> format("id: {0}, 고객명: {1} ", dto.getId() , dto.getName()))
                .toList();
    }
}
