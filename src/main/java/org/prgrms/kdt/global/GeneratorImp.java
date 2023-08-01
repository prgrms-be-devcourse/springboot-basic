package org.prgrms.kdt.global;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class GeneratorImp implements Generator{
    private GeneratorImp() {
    }

    @Override
    public UUID generateId() {
        UUID generateId = Generators.timeBasedGenerator().generate();
        String[] idArr = generateId.toString().split("-");
        return UUID.fromString(idArr[2]+"-"+idArr[1]+"-"+idArr[0]+"-"+idArr[3]+"-"+idArr[4]);
    }

    @Override
    public LocalDateTime generateTime() {
        return LocalDateTime.now();
    }
}
