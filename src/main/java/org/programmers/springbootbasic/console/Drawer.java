package org.programmers.springbootbasic.console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.*;

@Slf4j
@Component
public class Drawer {

    //TODO 동시성 문제 고려
    //TODO 로깅
    private static final StringBuffer viewAssembler = new StringBuffer();

    private static final String BASIC_RESOURCE_PATH = "/console-template/";
    private static final String BASIC_RESOURCE_TYPE = ".txt";
    private static final String START_SYMBOL_OF_ARGUMENT = "${";
    private static final String END_SYMBOL_OF_ARGUMENT = "}";

    ConsoleResponseCode draw(ModelAndView modelAndView) throws IOException {
        String view = modelAndView.getView();

        var viewTemplate = readTemplateFile(view);
        viewAssembler.append(viewTemplate);

        var model = modelAndView.getModel();
        applyArgument(model);

        System.out.println(viewAssembler);

        viewAssembler.delete(0, viewAssembler.length());
        var responseCode = modelAndView.getResponseCode();

        if (responseCode!= INPUT) {
            log.info("Clear model's attributes because attributes are used.");
            model.clear();
        }

        return responseCode;
    }

    private String readTemplateFile(String view) throws IOException {
        var resource = new ClassPathResource(BASIC_RESOURCE_PATH + view + BASIC_RESOURCE_TYPE);
        log.info("read resource: " + BASIC_RESOURCE_PATH + "{}", view + BASIC_RESOURCE_TYPE);
        String fileToString;
        try {
            File viewTemplate = resource.getFile();
            fileToString = Files.readString(viewTemplate.toPath());
        } catch (IOException e) {
            log.error("IOException thrown while reading {}{}{}", BASIC_RESOURCE_PATH, view, BASIC_RESOURCE_TYPE);
            throw e;
        }
        return fileToString;
    }

    private void applyArgument(Model model) {
        while (viewAssembler.indexOf(START_SYMBOL_OF_ARGUMENT) != -1) {
            int startOfArgument = viewAssembler.indexOf(START_SYMBOL_OF_ARGUMENT);
            int endOfArgument = viewAssembler.indexOf(END_SYMBOL_OF_ARGUMENT);

            String modelAttributeToStringView = convertModelAttributeToStringView(
                    model.getAttributes(viewAssembler.substring(
                            startOfArgument + START_SYMBOL_OF_ARGUMENT.length(), endOfArgument)));

            viewAssembler.replace(startOfArgument, endOfArgument + 1, modelAttributeToStringView);
        }
    }

    private String convertModelAttributeToStringView(Object modelAttribute) {
        if (modelAttribute instanceof String) {
            return (String) modelAttribute;
        } else if (modelAttribute instanceof Iterable<?>) {
            var stringBuffer = new StringBuffer();
            int sequence = 0;
            for (Object each : (Iterable) modelAttribute) {
                stringBuffer.append(++sequence);
                stringBuffer.append(". ");
                stringBuffer.append(each.toString());
                stringBuffer.append("\n");
            }
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
            return stringBuffer.toString();
        }
        return modelAttribute.toString();
    }
}
