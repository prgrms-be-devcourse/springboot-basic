package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.RedirectCommand.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final ConsoleMappingData mappingData;
    private final Drawer drawer;
    private final ConsoleProperties consoleProperties;

    public ConsoleResponseCode service(ConsoleRequest request) {
        try {
            //TODO: 이름 바꾸기
            //controller process
            //draw
            //catch exception
            var command = request.getCommand();
            var controller = mappingData.getController(command);
            ModelAndView modelAndView = controller.handleRequest(request);
            return drawer.draw(modelAndView);
        } catch (Exception e) {
            Model model = request.getModel();
            handleException(e, model);
            model.setRedirectLink(ERROR);
            return PROCEED;
        }
    }

    private void handleException(Exception e, Model model) {
        if (e instanceof IllegalStateException) {
            model.addAttributes("errorData",
                    (consoleProperties.isDetailErrorMessage()) ? e :
                            new ErrorData("프로그램 내부 연결 오류", ""));
        } else if (e instanceof IllegalArgumentException) {
            model.addAttributes("errorData",
                    (consoleProperties.isDetailErrorMessage()) ? e :
                            new ErrorData("잘못된 값을 입력",
                                    "적절한 값을 입력해주세요."));
        }
    }
}