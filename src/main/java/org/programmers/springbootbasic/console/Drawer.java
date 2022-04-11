package org.programmers.springbootbasic.console;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;

@Slf4j
public class Drawer {

    //TODO 동시성 문제 고려
    //TODO 로깅
    private static final StringBuffer viewAssembler = new StringBuffer();
    private static final String startSymbolOfArgument = "${";
    private static final String endSymbolOfArgument = "}";

    void draw(Model model, String view) throws IOException {
        var viewTemplate = readTemplateFile(view);
        viewAssembler.append(Files.readString(viewTemplate.toPath()));
        System.out.print(applyArgument(model));
        viewAssembler.delete(0, viewAssembler.length());
    }

    private File readTemplateFile(String view) throws IOException {
        var resource = new ClassPathResource("/console-template/" + view);
        log.info(MessageFormat.format("read resource: /console-template/{0}", view));
        File viewTemplate = resource.getFile();
        return viewTemplate;
    }

    private String applyArgument(Model model) {
        while (viewAssembler.indexOf(startSymbolOfArgument)!=-1) {
            int startOfArgument = viewAssembler.indexOf(startSymbolOfArgument);
            int endOfArgument = viewAssembler.indexOf(endSymbolOfArgument);
            log.info("startPlaceOfArg={}, endPlaceOfArg={}", startOfArgument, endOfArgument);

            String modelAttributeToStringView = convertModelAttributeToStringView(
                    model.getAttributes(viewAssembler.substring(
                            startOfArgument + startSymbolOfArgument.length(), endOfArgument)));

            viewAssembler.replace(startOfArgument, endOfArgument+1, modelAttributeToStringView);
        }
        return viewAssembler.toString();
    }

    private String convertModelAttributeToStringView(Object input) {
        if (input instanceof String) {
            return (String) input;
        }
        else if (input instanceof Iterable<?>) {
            var stringBuffer = new StringBuffer();
            int sequence = 0;
            for (Object each : (Iterable) input ) {
                stringBuffer.append(++sequence);
                stringBuffer.append(". ");
                stringBuffer.append(each.toString());
                stringBuffer.append("\n");
            }
            stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
            return stringBuffer.toString();
        }
        return input.toString();
    }
}
