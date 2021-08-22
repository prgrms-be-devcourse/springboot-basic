package org.prgrms.kdt;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.FileIOException;
import org.prgrms.kdt.member.application.MemberService;
import org.prgrms.kdt.member.domain.MemberProperties;
import org.prgrms.kdt.member.presentation.MemberController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

// MISSION-3
@SpringBootApplication
public class KdtSpringOrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        logger.info("logger name => " + logger.getName());
        logger.info("start system in => " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        SpringApplication springApplication = new SpringApplication(KdtSpringOrderApplication.class);
        logger.info("is springApplication -> ", springApplication);
        springApplication.setAdditionalProfiles("dev");
        ConfigurableApplicationContext run = springApplication.run(args);
        logger.info("springApplication run");
        MemberProperties memberProperties = run.getBean(MemberProperties.class);
        logger.info("get Bean MemberProperties -> " + memberProperties);
        List<String> lines = getCSV(run, memberProperties);
        MemberService memberService = run.getBean(MemberService.class);
        logger.info("get Bean MemberService -> " + memberService);
        new MemberController(memberService, lines).play();
        logger.info("START SYSTEM");
    }

    private static List<String> getCSV(ConfigurableApplicationContext run, MemberProperties memberProperties) {
        List<String> lines;
        Resource resource = run.getResource(memberProperties.getCsvFilePath());
        try {
            lines = Files.readAllLines(resource.getFile().toPath());
        } catch (IOException e) {
            throw new FileIOException(ErrorMessage.ERROR_READING_FILE, e);
        }
        logger.info("SUCCESS READ CSV " + lines);
        return lines;
    }

}
