package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.processor.Processor;
import org.prgrms.springbootbasic.util.CommandLineInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static org.prgrms.springbootbasic.type.MethodType.isExit;
import static org.prgrms.springbootbasic.type.ServiceType.number2ProcessorClass;

@Component
public class Console {

    @Value(value = "${notification.service}")
    private String serviceNotification;

    @Value(value = "${notification.exit}")
    private String exitNotification;

    @Autowired
    NotificationProperties notificationProperties;
    private final List<Processor> processorList;

    @Autowired
    public Console(List<Processor> controllerList) {
        this.processorList = controllerList;
    }

    public void run() {
        String selected;
        Processor processor;
        do {
            selected = CommandLineInput.getInput(serviceNotification);
            if (isExit(selected)) {
                System.out.println(exitNotification);
                return;
            }
            processor = searchProcessor(number2ProcessorClass(selected));
        } while (processor == null);

        System.out.println(processor.process());
        run();
    }

    private Processor searchProcessor(Class<? extends Processor> processorClass) {
        if (processorClass == null) {
            return null;
        }
        return processorList.stream()
                .filter(x -> isSelected(x, processorClass))
                .findFirst().get();
    }

    private boolean isSelected(Processor processor, Class<? extends Processor> selected) {
        return Objects.equals(processor.getClass(), selected);
    }
}
