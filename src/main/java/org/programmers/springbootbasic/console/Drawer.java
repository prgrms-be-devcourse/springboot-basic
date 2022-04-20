package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT_AND_REDIRECT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class Drawer {

    private final ConsoleProperties consoleProperties;

    private static final StringBuilder viewAssembler = new StringBuilder();

    private static final String BASIC_RESOURCE_PATH = "/console-template/";
    private static final String BASIC_RESOURCE_TYPE = ".txt";
    private static final String START_SYMBOL_OF_ARGUMENT = "${";
    private static final String END_SYMBOL_OF_ARGUMENT = "}";

    ConsoleResponseCode draw(ModelAndView modelAndView) {
        String view = modelAndView.getView();
        try {
            var viewTemplate = readTemplateFile(view);
            viewAssembler.append(viewTemplate);
        } catch (IOException e) {
            Model model = modelAndView.getModel();
            model.addAttributes("errorData",
                    (consoleProperties.isDetailErrorMessage()) ? e : new SimpleErrorMessageMapper.ErrorData("파일 읽기 오류", ""));
            model.setRedirectLink(ERROR);
            return PROCEED;
        }

        var model = modelAndView.getModel();
        applyArgument(model);

        System.out.println(viewAssembler);

        viewAssembler.delete(0, viewAssembler.length());
        var responseCode = modelAndView.getResponseCode();

        if (responseCode != INPUT_AND_REDIRECT) {
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
        if (modelAttribute instanceof String modelAttributeToString) {
            return modelAttributeToString;
        } else if (modelAttribute instanceof Iterable<?>) {
            var stringBuilder = new StringBuilder();
            int sequence = 0;
            for (Object each : (Iterable) modelAttribute) {
                stringBuilder.append(++sequence);
                stringBuilder.append(". ");
                stringBuilder.append(each.toString());
                stringBuilder.append("\n");
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            return stringBuilder.toString();
        }
        return modelAttribute.toString();
    }
}
