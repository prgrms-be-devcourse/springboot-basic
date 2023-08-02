package com.prgrms.common.util;

import java.time.LocalDateTime;
import com.fasterxml.uuid.Generators;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class GeneratorImp implements Generator {

    @Override
    public String makeKey() {
        UUID generateId = Generators.timeBasedGenerator().generate();
        String[] idArr = generateId.toString().split("-");

        return  idArr[2]+"-"+idArr[1]+"-"+idArr[0]+"-"+idArr[3]+"-"+idArr[4] ;
    }

    @Override
    public LocalDateTime makeDate() {
        return LocalDateTime.now();
    }

}
