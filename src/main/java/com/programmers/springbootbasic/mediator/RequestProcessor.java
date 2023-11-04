package com.programmers.springbootbasic.mediator;

import com.programmers.springbootbasic.infrastructure.IO.ConsoleInteractionAggregator;
import com.programmers.springbootbasic.mediator.provider.MenuRequestProvider;
import com.programmers.springbootbasic.presentation.ControllerAdapter;
import com.programmers.springbootbasic.presentation.MainMenu;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RequestProcessor {

    private final ControllerAdapter controllerAdapter;
    private final MenuRequestProvider menuRequestProvider;
    private final ConsoleInteractionAggregator consoleInteractionAggregator;

    public RequestProcessor(
        ControllerAdapter controllerAdapter,
        MenuRequestProvider menuRequestProvider,
        ConsoleInteractionAggregator consoleInteractionAggregator
    ) {
        this.controllerAdapter = controllerAdapter;
        this.menuRequestProvider = menuRequestProvider;
        this.consoleInteractionAggregator = consoleInteractionAggregator;
    }

    public void run() {
        ConsoleRequest request = getRequest();
        ConsoleResponse response = process(request);
        sendResponse(response);
    }

    public ConsoleRequest getRequest() {
        var menuInput = consoleInteractionAggregator.collectMenuInput();

        return menuRequestProvider.getMenuRequest(menuInput);
    }

    public ConsoleResponse process(ConsoleRequest request) {
        return MainMenu.routeToController(request, controllerAdapter);
    }

    public void sendResponse(ConsoleResponse response) {
        response.getBody().ifPresent(body -> {
            if (body instanceof List<?> listBody) {
                listBody.forEach(
                    item -> consoleInteractionAggregator.displayMessage(item.toString()));
            } else {
                consoleInteractionAggregator.displayMessage(body.toString());
            }
        });

        response.getMessage().ifPresent(
            message -> consoleInteractionAggregator.displayMessage(
                (String) message)
        );

    }

}
