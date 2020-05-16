package com.halo.mms.serv.controller.res;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mms")
public class EventResController {

    @RequestMapping("/event")
    public Object event() {
        return "event/index";
    }
}
