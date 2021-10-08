package org.prgrms.orderApp.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class CustomErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    private final String ERROR_PATH = "/errors";

    private final String ERROR_PAGE = "/error";

    @RequestMapping(value = "/error")
    public String handlerError(HttpServletRequest req, Model model) {
        var status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("now_date", LocalDateTime.now());
        model.addAttribute("title", "ERROR PAGE");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            logger.info(String.valueOf(statusCode));
            logger.info("어디야!!!!");
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("main_message", "[NOTICE] 찾으시는 페이지가 없습니다. 확인바랍니다.");
                return ERROR_PATH + ERROR_PAGE;
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("main_message", "[NOTICE] 서버에서 문제가 발생하였습니다. 담당자분에게 연락바랍니다.");
                return ERROR_PATH + ERROR_PAGE;
            }
        }
        logger.info("test");
        model.addAttribute("main_message", "[NOTICE] 문제가 발생하였습니다. 담당자분에게 연락바랍니다.");
        return ERROR_PATH+ERROR_PAGE;

    }


    public String getErrorPath(){
        return ERROR_PATH;
    }

}
