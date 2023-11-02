package com.programmers.springbootbasic.mediator.provider;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_MENU;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.mediator.requestGenerator.MenuRequestGenerator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MenuRequestProvider {

    List<MenuRequestGenerator> menuRequestGenerators;

    public MenuRequestProvider(List<MenuRequestGenerator> menuRequestGenerators) {
        this.menuRequestGenerators = menuRequestGenerators;
    }

    public ConsoleRequest getMenuRequest(String menuName) {
        return menuRequestGenerators.stream()
            .filter(menuRequestGenerator -> menuRequestGenerator.getMenuCommand().equals(menuName))
            .findFirst()
            .map(MenuRequestGenerator::generateRequest)
            .orElseThrow(() -> new CustomException(INVALID_MENU));
    }
}
