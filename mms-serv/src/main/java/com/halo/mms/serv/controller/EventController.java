package com.halo.mms.serv.controller;

import com.halo.mms.serv.core.SelContextHolder;
import com.halo.mms.serv.request.AddEventRequest;
import com.halo.mms.serv.service.api.EventDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/mms")
public class EventController {

    @Autowired
    private EventDayService eventDayService;

    @PostMapping("/event/add")
    @ResponseBody
    public Object addEvent(@RequestBody AddEventRequest request) {
        request.setNickName(SelContextHolder.getContext().getUserUuid());
        return eventDayService.addEvent(request);
    }

}
