package com.halo.mms.serv.service;

import com.halo.mms.out.result.BaseResult;
import com.halo.mms.repo.model.DayEventDO;
import com.halo.mms.repo.repository.DayEventRepository;
import com.halo.mms.serv.request.AddEventRequest;
import com.halo.mms.serv.request.ApiBaseRequest;
import com.halo.mms.serv.service.api.EventDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class EventDayServiceImpl implements EventDayService  {

    @Autowired
    private DayEventRepository dayEventRepository;

    @Override
    public BaseResult addEvent(AddEventRequest request) {
        DayEventDO dayEventDO = build(request);
        dayEventRepository.updateByUK(dayEventDO);
        return BaseResult.getSucc();
    }

    private DayEventDO build(AddEventRequest request) {
        DayEventDO dayEventDO = new DayEventDO();
        dayEventDO.setDay(request.getDay());
        dayEventDO.setEnd(Optional.ofNullable(request.getEnd()).map(Date::new).orElse(null));
        dayEventDO.setStart(Optional.ofNullable(request.getStart()).map(Date::new).orElse(null));
        dayEventDO.setNickName(request.getNickName());
        dayEventDO.setTag(request.getTag());
        return dayEventDO;
    }
}
