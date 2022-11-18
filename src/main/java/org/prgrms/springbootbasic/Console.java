package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.controller.Controller;
import org.prgrms.springbootbasic.type.ServiceType;
import org.prgrms.springbootbasic.util.CommandLineInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static org.prgrms.springbootbasic.type.MethodType.isExist;
import static org.prgrms.springbootbasic.type.ServiceType.number2ControllerClass;

@Component
public class Console {

    @Value(value = "${notification.service}")
    private String serviceNotification;

    @Value(value = "${notification.exit}")
    private String exitNotification;

    @Autowired
    NotificationProperties notificationProperties;
    private final List<Controller> processorList;

    @Autowired
    public Console(List<Controller> controllerList) {
        this.processorList = controllerList;
    }

    public void run() {
        String selected;
        Controller controller;
        do {
            selected = CommandLineInput.getInput(serviceNotification);
            if (isExist(selected)) {
                System.out.println(exitNotification);
                return;
            }
            controller = searchController(number2ControllerClass(selected));
        } while (controller == null);

        System.out.println(controller.process());
        run();
    }

    private Controller searchController(Class<? extends Controller> controllerClass) {
        if (controllerClass == null) {
            return null;
        }
        return processorList.stream()
                .filter(x -> isSelected(x, controllerClass))
                .findFirst().get();
    }

    private boolean isSelected(Controller controller, Class<? extends Controller> selected) {
        return Objects.equals(controller.getClass(), selected);
    }
}
