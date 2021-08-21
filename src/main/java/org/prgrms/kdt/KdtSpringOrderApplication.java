package org.prgrms.kdt;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.FileIOException;
import org.prgrms.kdt.member.application.MemberService;
import org.prgrms.kdt.member.domain.MemberProperties;
import org.prgrms.kdt.member.presentation.MemberController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

// MISSION-3
@SpringBootApplication
public class KdtSpringOrderApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(KdtSpringOrderApplication.class);
        springApplication.setAdditionalProfiles("memory");
        ConfigurableApplicationContext run = springApplication.run(args);
        MemberProperties memberProperties = run.getBean(MemberProperties.class);
        List<String> lines = getCSV(run, memberProperties);
        MemberService memberService = run.getBean(MemberService.class);
        new MemberController(memberService, lines).play();
    }

    private static List<String> getCSV(ConfigurableApplicationContext run, MemberProperties memberProperties) {
        List<String> lines;
        Resource resource = run.getResource(memberProperties.getCsvFilePath());
        try {
            lines = Files.readAllLines(resource.getFile().toPath());
        } catch (IOException e) {
            throw new FileIOException(ErrorMessage.ERROR_READING_FILE, e);
        }
        return lines;
    }

}
