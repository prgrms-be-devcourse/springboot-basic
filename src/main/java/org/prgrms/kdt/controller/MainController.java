package org.prgrms.kdt.controller;

import org.prgrms.kdt.function.VoucherProgramFunctions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        List<String> functionNames = new ArrayList<>();
        Arrays.stream(VoucherProgramFunctions.values())
                .forEach(c -> functionNames.add(c.name()));
        model.addAttribute(functionNames);
        return "home";
    }

    @GetMapping("/walletHome")
    public String walletHome() {
        return "voucherWallet/walletHome";
    }
}
