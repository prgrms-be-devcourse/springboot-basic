package org.programmers.springbootbasic.console.controller;

import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.springframework.stereotype.Component;

@Component
public interface Controller {
    void initCommandList();
    ModelAndView process(Command command, Model model);
    boolean supports(Command command);
}
